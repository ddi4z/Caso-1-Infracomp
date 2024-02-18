def verificar_archivos_iguales(archivo1, archivo2):
    with open(archivo1, 'r') as f1, open(archivo2, 'r') as f2:
        contenido1 = f1.read()
        contenido2 = f2.read()
    
    if contenido1 == contenido2:
        return True
    else:
        return False

# Ejemplo de uso
archivo1 = "salida.txt"
archivo2 = "esperado.txt"
if verificar_archivos_iguales(archivo1, archivo2):
    print("Los archivos son iguales.")
else:
    print("Los archivos son diferentes.")