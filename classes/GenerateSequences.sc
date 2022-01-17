GenerateSequences {
	var <>data;
	var <osc;
	var <synth;
	var <>onCall;
	var <start_pos, <end_pos;

	*new { | clk = 10, step = 1, len = 1, gesture = 1, linearity = 1 |
		// gesture == 1 >> ascending -- 0 >> descending
		// linearity == 1 >> TRUE (Line) -- 0 >> FALSE (XLine)
		^super.new.init(clk, step, len, gesture, linearity)
	}

	init { | clk, step, len, gesture, linearity |
		this.ugenconstr(clk, step, len, gesture, linearity);
		// OSC Responder
		this.oscrespond;
	}

	ugenconstr { | clk, step, len, gesture, linearity |
		Server.default.waitForBoot {
			case
			{ gesture == 1 }{
				start_pos = 1;
				end_pos = 2.1 * clk;
			}
			{ gesture == 0 }{
				start_pos = 2.1 * clk;
				end_pos = 1;
			};
			synth = Synth(\generator, [
				clk: clk,
				step: step,
				len: len,
				start_pos: start_pos,
				end_pos: end_pos,
				linearity: linearity
			]);
			synth.onFree(onCall)
		};
	}

	oscrespond {
		osc = OSCFunc.newMatching({ arg msg, time;
			//[time, msg].postln;
			var arr = msg[3..];
			data = data.add(arr);
		},'/analysis', Server.default.addr);
		^this.data
	}

	freeSynthOSC {
		fork {
			synth.free;
			osc.release;
		}
	}
}