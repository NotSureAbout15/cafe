import json
import secrets

import bcrypt
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import Trabajador, Mesas, Menu, Pedido


@csrf_exempt
def login(request):
    if request.method == 'POST':
        # compruebo q se envia un cuerpo en la peticion
        if not request.body:
            return JsonResponse({'error': 'No se ha enviado ningun cuerpo de petición'})

        body_json = json.loads(request.body)

        # compruebo q en el cuerpo de la peticion se envian el nombre y la contraseña
        if 'nombre' not in body_json or 'contra' not in body_json:
            return JsonResponse({'error': 'Faltan parámetros'})

        # meto en variable cada uno de los valores pasados en el cuerpo
        json_password = body_json['contra']
        json_name = body_json['nombre']

        # compruebo q el usuario esta en la bbdd
        try:
            user = Trabajador.objects.get(nombre=json_name)
        except Trabajador.DoesNotExist:
            return JsonResponse({'error': 'El usuario no existe'}, status=404)

        # compruebo q la contraseña q esta en la bbdd y la q paso el usuario son la misma
        if json_password == user.contrasena:
            # recojo el rol del usuario para devolverlo junto con el token
            rol = user.rol
            # creo el token de inicio de sesion y lo guardo en la bbdd
            token = secrets.token_hex(10)
            user.token = token
            user.save()
            return JsonResponse({'token': token, "rol": rol}, status=201)
        else:
            return JsonResponse({'error': 'Contraseña incorrecta'}, status=409)

    else:
        return JsonResponse({'error': 'Método no soportado'}, status=405)


@csrf_exempt
def inicio_mesa(request):
    if request.method == 'POST':
        # compruebo q se envia un cuerpo en la peticion
        if not request.body:
            return JsonResponse({'error': 'No se ha enviado ningún cuerpo de petición'})

        # recojo en una variable el cuerpo de la peticion
        body_json = json.loads(request.body)

        # compruebo q en el request body se envia el nombre de la mesa
        if 'mesa' not in body_json:
            return JsonResponse({'error': 'Faltan parámetros'}, status=405)

        # meto en una variable el nombre de la mesa
        nombre_mesa = body_json['mesa']

        # recojo la informacion de esa mesa
        try:
            mesa = Mesas.objects.get(nombre=nombre_mesa)
        except Mesas.DoesNotExist:
            return JsonResponse({'error': 'No se pudo encontrar la mesa'}, status=404)

        # compruebo el estado de la mesa, si esta en uso devuelvo un esrros, sino cambio el estado a en uso
        if mesa.uso == "S":
            return JsonResponse({'error': 'La mesa ya está en uso'}, status=409)
        else:
            try:
                mesa.uso = "S"
                mesa.save()
                estado = mesa.uso
                return JsonResponse({'estado': estado}, status=201)
            except Exception:
                return JsonResponse({'error': 'No se pudo cambiar el estado de la mesa'})


def menu(request, tipo):
    if request.method == 'GET':
        # recojo todos los elementos q esten en la tabla Menu
        menu_items = Menu.objects.filter(tipo=tipo).values()
        # los devuelvo a modo de lista
        return JsonResponse({'items': list(menu_items)})
    else:
        return JsonResponse({'error': 'Método no soportado'}, status=405)


@csrf_exempt
def pedido(request):
    if request.method == 'POST':
        # compruebo que se ha mandado el cuerpo de la peticion
        if not request.body:
            return JsonResponse({'error': 'No se ha mandado ningún pedido'})

        # recojo el pedido en una variable
        cuerpo_pedido = json.loads(request.body)

        # compruebo q se envian los parametros necesarios
        if 'mesa' not in cuerpo_pedido or 'nombre' not in cuerpo_pedido or 'precio' not in cuerpo_pedido or 'cantidad' not in cuerpo_pedido:
            return JsonResponse({'error': 'Faltan parámetros'}, status=405)

        # meto en variables cada uno de los campos
        nombre_pedido = cuerpo_pedido['nombre']
        mesa_pedido = cuerpo_pedido['mesa']
        precio_pedido = cuerpo_pedido['precio']
        cantidad_pedido = cuerpo_pedido['cantidad']

        # compruebo q la mesa existe
        try:
            mesa = Mesas.objects.get(nombre=mesa_pedido)
        except Mesas.DoesNotExist:
            return JsonResponse({'error': 'No se pudo encontrar la mesa'}, status=404)

        # el precio q se guarda en la bbdd de pedido sera el total del item (precio * cantidad)
        monto = precio_pedido * cantidad_pedido

        # añado el pedido a la bbdd
        try:
            Pedido.objects.create(pedido=nombre_pedido, mesa=mesa, precio=monto,  cantidad=cantidad_pedido)

            return JsonResponse({'estado': 'Producto añadido al pedido'}, status=201)
        except Exception as e:
            return JsonResponse({'error': 'No se ha podido añadir el producto al pedido: ' + str(e)})

    else:
        return JsonResponse({'error': 'Metodo no soportado'}, status=405)


def estado_mesa(request, nombreMesa):
    if request.method == 'GET':
        # compruebo q se envia un nombre de mesa
        if not nombreMesa:
            return JsonResponse({'error': 'No se ha mandado ninguna mesa'})

        # hago una consulta pasando el nombre de la mesa pasa saber su estado
        try:
            mesa = Mesas.objects.get(nombre=nombreMesa)
        except Mesas.DoesNotExist:
            return JsonResponse({'error': 'No se pudo encontrar la mesa'}, status=404)

        # guardo en una variable el estado de uso de dicha mesa para devolverlo en la peticion
        estado = mesa.uso
        return JsonResponse({"estado": estado}, status=200)

    else:
        return JsonResponse({'error': 'Metodo no soportado'}, status=405)
