# Project to keep social distance (CORONA)

The main idea was to enable people staying in distance by automated digital measures and alerts.
Target group could be workers or police officers running around in teams while having the focus on observing the area.

# Approaches

- Building an APP and observing the GPS signals
 - discarded, GPS is maybe not useful for a range of 2 meters
- Building an APP using direct WLAN communication
 - discarded, seams to be not feasible currently, effort unknown
- Building an APP using BlueTooth signal strength
 - accepted, feasible and attractive, because no additional tech needed
 
# Logical requirements for the BlueTooth approach
 
- there must be an correlation between the signal strength and the distance between the devices
- the signal must have an relevant precision in the range of 0-3 meters
- everyone involved needs an BlueTooth device to be trackable

# Milestones for the prototype

First of all we need to figure out, that the logical requirements we have are fulfilled.
All milestones are set to this main goal.

1. Get an running Android-APP deployed to a mobile device
2. Get access to the BlueTooth functionality and a list of surrounding devices
3. Get detailed information about the devices over RSSI displayed
4. Make Tests with a mobile device for multiple ranges and ensure the correlation
5. Optimize the usability
6. Detect other use-cases and improvements

# Evaluation

The research data can be found in the project_doc directory.
The results have shown, that there is a correlation between signal strength and distance.
But the correlation can not used to ensure the distance, because of temporary shifts in the signal strength probably depending on the environment, the mobile device status and the status of the tracked device.

The time for one single detection request on my phone was 5 seconds in average.
This is why I can not make multiple requests and take an average of the strength.

# Result

The basic technology seams to be not usable as it was implemented and I have no idea how to improve this.
I totally underestimated the importance of the time for the measurement.
To keep a relevant track of an moving process you need to measure every second I think.
If you need to take an average of multiple measurements to ignore peaks and deviations, then the time must be a fraction of a second.

# Conclusion

I had a lot of learnings about android APP development, JAVA and BlueTooth in the last two days and maybe I get an other idea for a project.
On my research I found a lot of tutorials which described exactly what I tried to figure out. There for I suspected that other developers before me also failed.

# Thanksgiving

A lot of thanks to the YouTube tutorial from "Good Morning" https://www.youtube.com/watch?v=xarmrbRA4E0