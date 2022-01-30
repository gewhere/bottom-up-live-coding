s.boot;

Server.killAll

{Latch.ar(Stepper.ar(Impulse.ar(Line.kr(1,99,9))),Impulse.ar(8))}.plot(9)

~anti_aliasing_factor = 4100/20000; // -> 0.205

// dur = 1, fs = 50Hz
~dur=1;~fs=50;{Latch.ar(Stepper.ar(Impulse.ar(Line.kr(1,~fs/2,~dur))),Impulse.ar(~fs+~fs*0.205))}.plot(~dur)


// group plot
// counter / non-aliasing / quasi-palindroms

(
~dur=1;~fs=500;{
	[
		Latch.ar(Stepper.ar(Impulse.ar(~fs+~fs*0.205)),Impulse.ar(~fs+~fs*0.205)),
		Latch.ar(Stepper.ar(Impulse.ar(Line.kr(1,~fs/2,~dur))),Impulse.ar(~fs+~fs*0.205)),
		Latch.ar(Stepper.ar(Impulse.ar(Line.kr(1,~fs*2,~dur))),Impulse.ar(~fs+~fs*0.205))
	]
}.plot(~dur, name:["counter", "non-aliasing", "quasi-palindroms"], separately: false)
)


Help.gui
Plotter