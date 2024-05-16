import json
import secrets

import bcrypt
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import Trabajador, Mesas, Menu


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
