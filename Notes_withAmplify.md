**Week 1** 
* 5/5/2020
  * research & decide which tools to use: firebase vs AWS amplify 
    * use amplify with react
* 5/6/2020
  * build a Amplify application from sample format (aws-amplify-quick-notes)
    * switch GitHub repo to CSSA_RateMyProf:
      * problem: can’t build
        * solution: in Build Setting: add this to prebuild: cd to the sub-directory 
      * problem: This page isn’t working, master.d225qrowdguv1y.amplifyapp.com redirected you too many times
        * solution: change build setting artifact: baseDirectory: aws-amplify-quick-notes/build
  * clean up data 
    * deside data format
    * merge multiple sheets of excel into one sheet
**TODO**
1. clean up data (complete empty field for all appropriate cells)
2. function for new department & new course
3. edge case: research
4. class taken: list


