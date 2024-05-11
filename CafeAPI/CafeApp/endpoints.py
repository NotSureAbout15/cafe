import json
import secrets

import bcrypt
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import Trabajador


@csrf_exempt
def login(request):
    if request.method == 'POST':
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
            # recojo el rol del usuario para devolverlo para devolverlo junto con el token
            rol = user.rol
            # creo el token de inicio de sesion y lo guardo en la bbdd
            token = secrets.token_hex(10)
            user.token = token
            user.save()
            return JsonResponse({'token': token, "rol": rol}, status=201)
        else:
            return JsonResponse({'error': 'Contraseña incorrecta'}, status=401)