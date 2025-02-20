# Load-Test-AMQBroker

## Overview

This project is a Quarkus-based application designed to perform load testing on AMQ Broker. It leverages Quarkus's capabilities for efficient resource utilization and rapid startup times, making it ideal for high-performance testing scenarios.

## Prerequisites

Before deploying this application to OpenShift, ensure you have the following:

- **Java Development Kit (JDK) 11 or 17**: Verify that the JAVA_HOME environment variable is set accordingly.
- **Apache Maven 3.8.6 or later**: Ensure Maven is installed and accessible in your system's PATH.
- **OpenShift CLI (oc tool)**: Install the latest compatible version and ensure you have access to an OpenShift cluster.
- **Podman**: Install Podman for container image building.
- **Quarkus Extensions**: The project should include the quarkus-openshift and quarkus-container-image-podman extensions. If not, add them using the commands below.

## Adding Quarkus Extensions

If the required extensions are not already included in your project, add them by running:

```bash
./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-openshift,io.quarkus:quarkus-container-image-podman"
```

## Building the Container Image with Podman

To build the container image, execute the following command:

```bash
podman build -f Dockerfile.jvm -t quay.io/tavelino/amqTest
```

## Pushing the Image to Quay.io

Once the image is built, log in to Quay.io and push the image using the commands below:

```bash
podman login quay.io
podman push quay.io/tavelino/amqTest
```

Ensure you have the correct credentials and permissions to push images to Quay.io.

## Deploying to OpenShift

Once the image is available in Quay.io, you can deploy it to OpenShift using the OpenShift CLI (`oc`) and the Quarkus OpenShift extension.

```bash
oc new-app quay.io/tavelino/amqTest --name=load-test-amqbroker
```

After deployment, verify the status of your application using:

```bash
oc get pods
```

For further debugging and logs:

```bash
oc logs -f deployment/load-test-amqbroker
```

## Installing Tempo Operator for Request Observability

## Installing k6 Operator for Load Testing

To install the k6 Operator, which facilitates running distributed load tests within your OpenShift cluster, execute the following command:

```bash
curl https://raw.githubusercontent.com/grafana/k6-operator/main/bundle.yaml | oc n apply -f -
```

This command applies the necessary resources to deploy the k6 Operator in your cluster.

### Verifying the Installation

Once installed, verify that the k6 Operator is running by checking the deployed pods:

```bash
kubectl get pods -n k6-operator-system
```

Now, we back to this repository folder and go create a configmap with our K6 test plan:

```bash
oc project k6-operator-system
cd k6
oc create configmap k6-api-test --from-file load-test.js
```

Finally, we do create a k6 instance with this command:

```bash
oc apply -f k6-sample.yaml
```

For more details about the k6 Operator and its capabilities, refer to the official documentation:
[Grafana k6 Operator Documentation](https://grafana.com/docs/k6/latest/set-up/set-up-distributed-k6/install-k6-operator/)

You can also watch this tutorial for a step-by-step guide:
[k6 Load Testing on Kubernetes using the k6 Operator](https://www.youtube.com/watch?v=IJ0uQgn7gI8)

TODO: TRABALHAR EM UM PROJETO DE BALANCEAMENTO DE CARGA ENTRE NÓS
