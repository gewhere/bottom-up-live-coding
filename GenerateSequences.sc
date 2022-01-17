GenerateSequences {
	var <>data;
	var osc;
	var <>synth;
	var <>onCall;

	*new { | clk = 10, step = 1, len = 1, gesture = 1, linearity = 1 |
		// gesture == 1 >> ascending
		// linearity == 1 >> TRUE
		^super.new.init(clk, step, len, gesture, linearity)
	}

	init { | clk, step, len, gesture, linearity |
		this.ugenconstr(clk, step, len, gesture, linearity);
		// OSC Responder
		this.oscrespond;
	}

	ugenconstr { | clk, step, len, gesture, linearity |
		Server.default.waitForBoot {
			var env, imp, notifier, latch;
			var lin, start_pos, end_pos;
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
		// osc = OSCdef(\listener, { | msg |
		// 	var arr = msg[3..];
		// 	data = data.add(arr);
		// }, '/analysis');
	}

}