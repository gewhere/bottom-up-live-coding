Help.gui;
// Generate zero length sequences

s.boot


(
SynthDef(\LHCVlin, { | out=0, clk=1, id=0, step=0, dur=1, lo=1, hi=1 |
	var sampleClock, varClock, stepper, line;
	line = Line.kr(lo, hi, dur, doneAction: 2);
	sampleClock = Impulse.kr(clk);
	varClock = Impulse.kr(line);
	stepper = Stepper.kr(varClock, step: step);
	Out.kr(out, SendTrig.kr(sampleClock, id, stepper))
}).add;
SynthDef(\LHCVexp, { | out=0, clk=1, id=0, step=0, dur=1, lo=1, hi=1 |
	var sampleClock, varClock, stepper, line;
	line = XLine.kr(lo, hi, dur, doneAction: 2);
	sampleClock = Impulse.kr(clk);
	varClock = Impulse.kr(line);
	stepper = Stepper.kr(varClock, step: step);
	Out.kr(out, SendTrig.kr(sampleClock, id, stepper))
}).add;
)


(
~fs = 50; //  up to 50 Hz works properly
~bar = 0.5; // period of actual prototype
~period = 1 / ~fs;
~dur = ((1..20) / 4);
~low = 0.01;
~high = 5.0;
{
	~dur do: { | idx |
		x = Synth(\LHCfixed, [\clk, ~fs, \dur, idx, \step, 1]);
		~synthName = x.defName;
		f = HuffmanDecoder.new;
		~counterOut = [];
		~encodedSymbols = [];
		~modifyStep = (A: 1, B: 3, C: 5, D: 7);
		~osc = OSCFunc.newMatching({ arg msg, time;
			var countInt;
			countInt = msg[3].asInteger;
			~counterOut = ~counterOut add: countInt;
			~binaryString = countInt.asBinaryDigits(3);
			~binaryString do: { |bit|
				var theOutput;
				theOutput = f.input(bit);
				~encodedSymbols = ~encodedSymbols add: theOutput;
				if(theOutput.notNil){
					//~modifyStep[theOutput].postln;
					x.set(\step, ~modifyStep[theOutput]);
				}
			};
		},'/tr', Server.default.addr);
		idx.wait;
		x.free;
		~osc.free;
		"OSC released and plot".postln;
		//(synthName, fs, dur, counterOut, encodedSymbols)
		PrintData(~synthName, ~fs, idx, ~counterOut, ~encodedSymbols);
	};
}.fork(AppClock);
)
