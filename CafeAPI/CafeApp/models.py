from django.db import models


class Trabajador(models.Model):
    nombre = models.CharField(max_length=70)
    contrasena = models.CharField(max_length=10)
    token = models.CharField(max_length=50, unique=True)
    email = models.CharField(max_length=30)
    telefono = models.CharField(max_length=9)
    turno = models.CharField(max_length=25)
    rol = models.CharField(max_length=10, default="Trabajador")


class Mesas(models.Model):
    nombre = models.CharField(max_length=50, unique=True, null=False)
    uso = models.CharField(max_length=1, default="N")


class Menu(models.Model):
    nombre = models.CharField(max_length=50, null=False)
    tipo = models.CharField(max_length=9)
    precio = models.IntegerField(null=False)


class Pedido(models.Model):
    pedido = models.ForeignKey(Menu, on_delete=models.CASCADE, related_name="comandas")
    mesa = models.ForeignKey(Mesas, on_delete=models.CASCADE)
    precio = models.ForeignKey(Menu, on_delete=models.CASCADE, related_name="monto")
    cantidad = models.IntegerField(null=False)
