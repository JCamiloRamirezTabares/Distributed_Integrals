## 1 = Monte Carlo
## 2 = Riemann  

N_POINTS=("1000" "10000" "100000" "1000000" "10000000" "100000000" "1000000000")
function="x^2"
lowerRange="0"
upperRange="1"


cd "../client/build/libs"

for points in "${N_POINTS[@]}"
do
    java -jar client.jar test 1 $points $function $lowerRange $upperRange
done