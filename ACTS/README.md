# Advanced Combinatorial Testing System (ACTS)
ACTS is a free combinatorial test generation tool developed by NIST.

No license is required and there are no restrictions on use.

## Executing ACTS
ACTS is a Java application.  You need to have Java installed on your system to run it.  You can download Java from https://www.java.com/en/download/.

To run ACTS, download the ACTS 3.3 jar file from the link below.  Then open a command prompt (terminal) and navigate to the directory where you saved the jar file.  Run the following command:

```shell
java -jar acts_3.3.jar
```

## Downloads

ACTS 3.3 download:
https://drive.google.com/file/d/1R5QmGty50f-HZHjey1rVnV9VUmMuSC33/view?usp=sharing

### Additional Tools

ACTS 3.2 download:
https://drive.google.com/file/d/1y5b17bX3Ov6xJ0ncpu1bpWDtwaiJtLd3/view?usp=sharing

CFD Github Repo: Combination Frequency Difference tool
https://github.com/FDurso1/Combination-Frequency-Differencing-Tool/

CCM tool download:
https://drive.google.com/file/d/1PeVwTtmFB3PToJkOsN791LDfkYwht37i/view?usp=sharing

Example_SUT.zip – illustrates ACTS use
https://drive.google.com/file/d/1vI2F-He-sGCTksyLVMDJtjOj9pgFuoik/view?usp=sharing

## Tutorials and Guides
Tutorial on combinatorial methods for software testing (pdf in [English](https://csrc.nist.rip/library/alt-SP800-142-101006.pdf)).

[ACTS User Guide](https://csrc.nist.gov/csrc/media/Projects/automated-combinatorial-testing-for-software/documents/acts_user_guide_for_basic1.0_and_advanced3.3_versions_nov23.pdf) - how to use the ACTS test generation tool

[Combinatorial Coverage Measurement](https://csrc.nist.gov/CSRC/media/Projects/Automated-Combinatorial-Testing-for-Software/documents/ComCoverage110130.pdf) - explains various coverage measurements and how to use the tool for computing these.  There is also a [manual for the command line version](https://csrc.nist.gov/CSRC/media/Projects/automated-combinatorial-testing-for-software/documents/Combinatorial%20Coverage%20Measurement%20Command%20Line%20Tool%20-%20User%20Manual.pdf) of the CCM tool.

[Fault ID user manual](https://csrc.nist.gov/CSRC/media/Projects/automated-combinatorial-testing-for-software/documents/FaultID%20User%20Guide.pdf) - for tool that helps identify likely fault-triggering combinations in failing tests.

[PEV tool user manual](https://csrc.nist.gov/CSRC/media/Projects/automated-combinatorial-testing-for-software/documents/pev_guide_2.pdf) – for testing rule-based expert systems or business rule engine/workflow systems.

## More Information
Presentations, FAQ, and papers:  https://csrc.nist.gov/acts

Source for some of the tools: https://github.com/usnistgov/combinatorial-testing-tools

The PEV tool for rule-based systems, such as business rule engines and expert systems:
* https://github.com/usnistgov/combinatorial-testing-tools/blob/master/pev.jar
* https://github.com/usnistgov/combinatorial-testing-tools/blob/master/pev-cli.jar
* https://github.com/usnistgov/combinatorial-testing-tools/blob/master/pev_guide_2.pdf

A classification tree editor to specify input model partitions, and then feed the result to ACTS to produce covering arrays. https://github.com/comtest/comtestnist/releases