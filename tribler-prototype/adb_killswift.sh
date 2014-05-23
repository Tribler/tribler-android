#!/bin/sh

PID=`adb shell ps | grep swift | awk '{print $2}'`

if [ -n "${PID}" ]
then
	echo "Found swift process with PID#${PID}, killing.."
	adb shell "su -c 'kill -9 ${PID}'"
else
	echo "No swift process found"
fi

