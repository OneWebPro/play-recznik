#!/bin/bash

play="/home/user/play/play"

if [ ! -f RUNNING_PID ]; then
    `$play start &`
else
	for i in `cat RUNNING_PID`
	do
		echo `kill -9 $i`
	done
	`rm RUNNING_PID`
	`$play start &`
fi