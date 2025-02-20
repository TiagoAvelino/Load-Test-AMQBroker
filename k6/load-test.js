import { check } from 'k6';
import http from 'k6/http';

export const options = {
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '60s', // total duration
            preAllocatedVUs: 10, // to allocate runtime resources

            rate:100, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
};

var i = 0;
    export default function () {
        const res = http.get('http://amqp-application-mqtt-producer-horus.apps.tiago-cluster.sandbox2217.opentlc.com/message');

        check(res, {
            'Post status is 200': (r) => res.status === 200
        });
    }