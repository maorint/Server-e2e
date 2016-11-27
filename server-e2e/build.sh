#!/bin/sh

. $(dirname $0)/version.sh

fatal() { echo "ERROR: $1"; exit 1; }

usage() {
  echo "Compile, test application. Build and push a docker image wrapper script."
  echo "Usage:"
  echo "  $(basename $0) compile - compile the java application"
  echo "  $(basename $0) test    - compile and run unit tests"
  echo "  $(basename $0) build   - create and tag a docker image"
  echo "  $(basename $0) push    - push docker image to registry"
  echo "  $(basename $0) remove  - untag (or delete) a docker image"
}

case $1 in
  test)
    shift; # remove first argument ('test') from $@
    # exec makes sure gradle exit code is used as exit from this script
    exec ./gradlew clean test $@
    ;;
  *)
    usage
    exit 1
esac
