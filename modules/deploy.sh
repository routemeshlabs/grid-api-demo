#!/usr/bin/env bash
GS_HOME=${GS_HOME=`(cd ../../; pwd )`}
$GS_HOME/bin/gs.sh pu deploy my-pu-stateful target/my-pu-stateful-0.1.jar $*