# Definición de los servidores, clientes y broker
servers=("xhgrid2" "xhgrid3")
clients=("xhgrid11" "xhgrid12")
broker="xhgrid1"

# Ruta de los archivos .jar para servidor y cliente
server_jar_path="../server/build/libs/server.jar"
client_jar_path="../client/build/libs/client.jar"
broker_jar_path="../broker/build/libs/broker.jar"

# Credenciales
password=$(<../credentials/password.txt)

# Crea directorios en los servidores, clientes y el broker
all_devices=("${servers[@]}" "${clients[@]}" "$broker")

for device in "${all_devices[@]}"
do
    echo "Verificando directorio en $device..."
    sshpass -p "$password" ssh swarch@"$device" '[[ -d Documents/RyM ]] || mkdir -p Documents/RyM'
done

# Despliega el .jar en el broker
echo "Desplegando broker..."
scp "$broker_jar_path" "swarch@$broker:Documents/RyM/"

# Despliega el .jar en los servidores
for server in "${servers[@]}"
do
    echo "Desplegando en servidor $server..."
    scp "$server_jar_path" "swarch@$server:Documents/RyM/"
done

# Despliega el .jar en los clientes
for client in "${clients[@]}"
do
    echo "Desplegando en cliente $client..."
    scp "$client_jar_path" "swarch@$client:Documents/RyM/"
done

echo "Despliegue completado."