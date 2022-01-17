// Boot sound engine (SC server)
s.boot;

// Generate sequential data

(
d = IdentityDictionary.new;
~name = [];
~data = [];
~clk = 500; // clock rate in Hz
~step = [1, 3, 5, 7]; ~len = 1; // ~len in seconds
~ascend = 1; // Ascending envelope values on the variable clock (0, descending values)
~linear = 1; // 1 for Linear envelope on the variable clock (0, for XLine)
fork{
	~step do: { | i |
		g = GenerateSequences(~clk, i, ~len, ~ascend, ~linear);
		g.onCall_({
			h = HuffmanEncoder(g.data);
			t = TokenRecognition(h.states);
			q = t.tokensProba(t.all_tokens);
			~name = ~name.add(~fn);
			~data = ~data.add(q.tokensListCoded);
			format("Counter input = %\n\tcounter-data-size = %", i, g.data.size).postln;
			format("\tencoder-states-size = %", h.states.size).postln;
			format("\tall_tokes.size = %", t.cnt2).postln;
			format("\tTokens decimal coding = %", q.tokensListCoded[0..10]).postln;
		});
		g.freeSynthOSC;
		(~len + 0.5).wait;
		d['step_' ++ i.asSymbol ++ '_data'] = g.data;
		d['step_' ++ i.asSymbol ++ '_states'] = h.states; // states seq
		d['step_' ++ i.asSymbol ++ '_tokens'] = t.all_tokens; // tokens seq
		d['step_' ++ i.asSymbol ++ '_tokensCoded'] = q.tokensListCoded; // tokens seq
	};
}
)

// Plot the histograms for the generated token sequences
// The tokens have been coded to decimal representation (see method tokensProba in the class TokenRecognition)

(
~hist_1 = d[d.keys.asArray.sort[3]]; // counter sequences with input: 1
~hist_3 = d[d.keys.asArray.sort[7]]; // counter sequences with input: 3
~hist_5 = d[d.keys.asArray.sort[11]]; // counter sequences with input: 5
~hist_7 = d[d.keys.asArray.sort[15]]; // counter sequences with input: 7
~hist_1.histo(7).plot(name: "[hist] input: 1", minval:0, maxval:20);
~hist_3.histo(7).plot(name: "[hist] input: 3", minval:0, maxval:20);
~hist_5.histo(7).plot(name: "[hist] input: 5", minval:0, maxval:20);
~hist_7.histo(7).plot(name: "[hist] input: 7", minval:0, maxval:20);
a = ~hist_1.plot(name: "[seq] input: 1", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
a = ~hist_3.plot(name: "[seq] input: 3", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
a = ~hist_5.plot(name: "[seq] input: 5", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
a = ~hist_7.plot(name: "[seq] input: 7", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
)

// Correlations between histograms -- should be represented as QQ-plots

corr(~hist_1.histo(7), ~hist_3.histo(7))
corr(~hist_1.histo(7), ~hist_5.histo(7))
corr(~hist_1.histo(7), ~hist_7.histo(7))
corr(~hist_3.histo(7), ~hist_5.histo(7))
corr(~hist_3.histo(7), ~hist_7.histo(7))
corr(~hist_5.histo(7), ~hist_7.histo(7))


(
d.keys.asArray.sort do: { | step |
	format("% >", step).postln;
	//format("step: %", step).postln;
	case
	{ step.asString.contains("data") }{
		"\t\t> data_list: ".post; d[step][1..5].postln;
	}
	{ step.asString.contains("states") }{
		"\t\t> states_list: ".post; d[step][1..5].postln;
	}
	{ step.asString.contains("tokens") }{
		"\t\t> tokens_list: ".post; d[step][1..5].postln;
	};
};
)
