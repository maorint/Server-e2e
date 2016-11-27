#!/bin/sh

. $(dirname $0)/version.sh

export SERVICE_NAME
export DOCKER_REGISTRY
export DOCKER_IMAGE_NAME

machineIp="127.0.0.1"
unamestr=`uname`
if [ "$unamestr" != 'Linux' ]; then
   machineIp=$(docker-machine ip)
else
   machineIp=$(hostname -I | awk '{print $1}')
fi

export EXTERNAL_IP="$machineIp"

exec docker-compose $@
