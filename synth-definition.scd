s.boot;

(
SynthDef(\generator, { | clk, step, len, start_pos, end_pos, linearity |
	var env, imp, notifier, latch, lin;
	lin = [Line.kr, XLine.kr];
	env = Select.kr(linearity, lin);
	imp = Impulse;
	notifier = Changed.kr(
		latch = Latch.kr(
			Stepper.kr(imp.kr(env), step: step), imp.kr(clk)
		)
	);
	//SendReply.kr(notifier, '/analysis', values: [latch, env/clk], replyID: [9900,9901]);
	SendReply.kr(notifier, '/analysis', values: [latch, env/clk]);
}).writeOnce;
)

SynthDef.synthDefDir
