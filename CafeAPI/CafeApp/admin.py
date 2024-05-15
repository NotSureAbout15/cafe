from django.contrib import admin

from .models import Trabajador,Mesas,Menu,Pedido

admin.site.register(Trabajador)
admin.site.register(Mesas)
admin.site.register(Menu)
admin.site.register(Pedido)
