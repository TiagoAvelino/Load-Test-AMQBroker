import { check } from 'k6';
import http from 'k6/http';

export const options = {
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '10s', // total duration
            preAllocatedVUs: 2, // to allocate runtime resources

            rate: 50, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
};

var i = 0;
    export default function () {
        const res = http.get('endpoint-cluster/message');

        check(res, {
            'Post status is 200': (r) => res.status === 200
        });
    }

//curl -X POST \
// http://localhost:8080/mqtt/send?topic=mqtt-message-in/1/2/app/test \
// -H "Content-Type: application/json" \
// -H "Accept: */*" \
// -d '{
//       "message": "teste1",
//       "jwt": "teste"
//     }'


// curl -X POST "http://mqtt-producer-horus.apps.tiago-cluster.sandbox3073.opentlc.com/mqtt/send?topic=mqtt-message-in/1/2/app/test" \
// -H "Content-Type: application/json" \
// -H "Accept: */*" \
// -d '{"message": "teste1", "jwt": "teste"}'
