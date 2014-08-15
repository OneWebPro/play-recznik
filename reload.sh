#!/bin/bash

if [ ! -f RUNNING_PID ]; then
    `activator "start -Dhttp.port=8882"`
else
	for i in `cat RUNNING_PID`
	do
		echo `kill -9 $i`
	done
	`rm RUNNING_PID`
	`activator "start -Dhttp.port=8882"`
fi