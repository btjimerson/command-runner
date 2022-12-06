if [ "$1" == "" ]; then
    echo "The first argument should be the API's base url." 1>&2
    exit 1
fi

if [ "$2" == "" ]; then
    echo "The second argument should be the number of calls." 1>&2
    exit 1
fi

baseurl=$1
loops=$2

x=1
while [ $x -le $loops ]
do

curl --location --request POST "$baseurl/api/v1/command" \
--data-raw '{"command": "curl -iv -X GET httpbin.org/get -H '\''accept: application/json'\''"}' \
-H "Content-Type: application/json"

curl --location --request POST "$baseurl/api/v1/command" \
--data-raw '{"command": "curl -iv https://www.cbssports.com"}' \
-H "Content-Type: application/json"

curl --location --request POST "$baseurl/api/v1/command" \
--data-raw '{"command": "dig www.google.com"}' \
-H "Content-Type: application/json"

x=$(( $x +1 ))
done
