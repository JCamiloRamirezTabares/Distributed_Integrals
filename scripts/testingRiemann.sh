## 1 = Monte Carlo
## 2 = Riemann  

PARTITIONS=("1000" "10000" "100000" "1000000" "10000000" "100000000" "1000000000")
function="2*(1-x^2)^0.5"
lowerRange="-1"
upperRange="1"


cd "../client/build/libs"

for partition in "${PARTITIONS[@]}"
do
    java -jar client.jar test 2 $partition $function $lowerRange $upperRange
done