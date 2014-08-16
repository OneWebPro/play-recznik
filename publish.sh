#!/bin/bash

activator compile test
test -f target/universal/stage/RUNNING_PID && kill `cat target/universal/stage/RUNNING_PID` && sleep 5;
test -f target/universal/stage/RUNNING_PID && rm target/universal/stage/RUNNING_PID;
activator clean stage;
target/universal/stage/bin/recznik -Dhttp.port=8882 &