PrintData {
	var <symbolicStream;
	var <encodedSymbols;
	var <all_tokens;
	var <tokens, <all_tokens_coded;
	var <gesture;
	var <string;
	var <actual_dur;
	var <bar = 0.5;


	*new { | synthName, fs, dur, counterOut, encodedSymbols |
		^super.new.init(synthName, fs, dur, counterOut, encodedSymbols);
	}

	init { | synthName, fs, dur, counterOut, encodedSymbols |
		this.dataPreprocessing(synthName, fs, dur, counterOut, encodedSymbols);
	}

	dataPreprocessing { | synthName, fs, dur, counterOut, encodedSymbols |
		var period, t;
		var get_tokens, get_tokens_coded;
		var file, pwd, fn;

		period = 1 / fs;
		actual_dur = (bar / period) * dur;

		symbolicStream = encodedSymbols collect: { |x| x ? -1 };
		tokens = TokenRecognition(encodedSymbols);
		get_tokens = tokens.all_tokens;
		get_tokens_coded = tokens.tokensProba(get_tokens);
		format("Condition:\t\t\t == % == ", synthName).postln;
		format("Duration (actual):\t % sec", actual_dur).postln;
		format("Tokens/sec:\t\t\t %", get_tokens.size / actual_dur).postln;
		format("Tokens generated:\t %", get_tokens.size).postln;
	}
}