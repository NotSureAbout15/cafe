# Generated by Django 5.0.2 on 2024-05-17 11:43

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('CafeApp', '0003_alter_menu_precio'),
    ]

    operations = [
        migrations.AlterField(
            model_name='pedido',
            name='pedido',
            field=models.CharField(max_length=50),
        ),
        migrations.AlterField(
            model_name='pedido',
            name='precio',
            field=models.FloatField(),
        ),
    ]
