"""
URL configuration for CafeAPI project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/5.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from CafeApp import endpoints

urlpatterns = [
    path('admin/', admin.site.urls),
    path('login', endpoints.login),
    path('iniciomesa', endpoints.inicio_mesa),
    path('menu/<tipo>', endpoints.menu),
    path('anadirpedido', endpoints.pedido),
    path('cerrarsesion', endpoints.cerrar_sesion_trabajador),
    path('liberarmesa/<nombreMesa>', endpoints.liberar_mesa),
    path('estadomesa/<nombreMesa>', endpoints.estado_mesa),
    path('verpedido/<nombreMesa>', endpoints.ver_pedido),
    path('trabajadores', endpoints.listadotrabajadores)
]
