s.boot;


(
d = IdentityDictionary.new;
~name = [];
~data = [];
~clk = 500;
~step = [1, 3, 5, 7]; ~len = 1;
~ascend = 1;
~linear = 1;
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

d.keys.asArray.sort

(
~hist_3 = d[d.keys.asArray.sort[3]];
~hist_7 = d[d.keys.asArray.sort[7]];
~hist_11 = d[d.keys.asArray.sort[11]];
~hist_15 = d[d.keys.asArray.sort[15]];
~hist_3.histo(7).plot(name: "3", minval:0, maxval:20);
~hist_7.histo(7).plot(name: "7", minval:0, maxval:20);
~hist_11.histo(7).plot(name: "11", minval:0, maxval:20);
~hist_15.histo(7).plot(name: "15", minval:0, maxval:20);
a = ~hist_3.plot(name: "3", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
a = ~hist_7.plot(name: "7", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
a = ~hist_11.plot(name: "11", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
a = ~hist_15.plot(name: "15", minval:0, maxval:8);a.plotMode = \steps; a.refresh;
)


corr(~hist_3.histo(7), ~hist_7.histo(7))
corr(~hist_3.histo(7), ~hist_11.histo(7))
corr(~hist_3.histo(7), ~hist_15.histo(7))
corr(~hist_7.histo(7), ~hist_11.histo(7))
corr(~hist_7.histo(7), ~hist_15.histo(7))
corr(~hist_11.histo(7), ~hist_15.histo(7))


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

///
Help.gui