# Bottom-up live coding: Analysis of continuous interactions towards predicting programming behaviours

* How to execute the code
- The classes should be moved (or linked) to the folder `Platform.userExtensionDir`
- Recompile SC class library `thisProcess.recompile`
- The scripts `generate-data-sequences*.scd` are generating the datasets based on the clock indicators (eg. 5x)
- Run the code blocks inside each script
	+ The script `generate-data-sequences-fixed.scd` only prints out the data (0-length sequences)
	+ To store the data on your computer go to `./classes/WriteData.sc` and configure the path in line 50.
