#!/bin/bash

# URL to send the GET request to
URL="http://amqp-application-amq.apps.tiago-cluster.sandbox1098.opentlc.com/message"

# Interval between requests in seconds
INTERVAL=0.01

# Function to send a GET request
send_request() {
    response=$(curl -s -w "\nHTTP_STATUS_CODE:%{http_code}\n" "$URL")
    body=$(echo "$response" | sed -n '1,/HTTP_STATUS_CODE:/p' | sed '$d')
    status_code=$(echo "$response" | sed -n 's/.*HTTP_STATUS_CODE://p')

    echo "HTTP Status Code: $status_code"
    echo "Response Body:"
    echo "$body"
    echo "-----------------------------"
}

# Infinite loop to send requests at specified intervals
while true; do
    send_request
    sleep $INTERVAL
done
