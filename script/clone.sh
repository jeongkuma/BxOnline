#!/bin/bash

rm -rf conv.jar
rm -rf /home/iabacus/conv/convproject
git clone http://115.90.93.3:1443/Convergence/convproject.git
cd /home/iabacus/conv/convproject && gradle bootjar
mv /home/iabacus/conv/convproject/build/libs/conv.jar /home/iabacus/conv/.