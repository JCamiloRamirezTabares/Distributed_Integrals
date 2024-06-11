# 1 = Monte Carlo
## 2 = Riemann  
POINTS=("1000")
function="2*(1-x^2)^0.5"
lowerRange="-1"
upperRange="1"
client="hgrid15"

# Bucle for para ejecutar las operaciones en diferentes particiones
for point in "${POINTS[@]}"
do
    # Conexión SSH y ejecución del comando java
    ssh swarch@"$client" "
    cd Documents/RyM &&
    java -jar client.jar test 1 $point '$function' $lowerRange $upperRange" &
    sleep 65
done

# Pausa de 2 minutos después de iniciar todas las sesiones SSH
exit 0