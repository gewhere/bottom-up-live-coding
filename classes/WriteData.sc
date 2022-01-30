WriteData {
	var <symbolicStream;
	var <encodedSymbols;
	var <all_tokens;
	var <tokens, <all_tokens_coded;
	var <gesture;
	var <string;
	var <actual_dur;
	var <bar = 0.5;


	*new { | synthName, fs, dur, low, high, counterOut, encodedSymbols |
		^super.new.init(synthName, fs, dur, low, high, counterOut, encodedSymbols);
	}

	init { | synthName, fs, dur, low, high, counterOut, encodedSymbols |
		this.dataPreprocessing(synthName, fs, dur, low, high, counterOut, encodedSymbols);
	}

	dataPreprocessing { | synthName, fs, dur, low, high, counterOut, encodedSymbols |
		var period, t;
		var get_tokens, get_tokens_coded;
		var file, pwd, fn;

		period = 1 / fs;
		actual_dur = (bar / period) * dur;

		symbolicStream = encodedSymbols collect: { |x| x ? -1 };
		tokens = TokenRecognition(encodedSymbols);
		get_tokens = tokens.all_tokens;
		get_tokens_coded = tokens.tokensProba(get_tokens);
		if(low > high){ gesture = "descend" }{ gesture = "ascend" };
		format("Condition:\t\t\t == % == ", synthName).postln;
		format("Gesture:\t\t\t == % == ", gesture).postln;
		format("Duration (actual):\t % sec", actual_dur).postln;
		format("Tokens/sec:\t\t\t %", get_tokens.size / actual_dur).postln;
		format("Tokens generated:\t %", get_tokens.size).postln;
		string = format("type\tdata\n");
		string = string ++ format("condition\t%\n", synthName);
		string = string ++ format("gesture\t%\n", gesture);
		string = string ++ format("sampling_rate\t%\n", fs);
		string = string ++ format("duration_actual\t%\n", actual_dur);
		string = string ++ format("tokens_per_sec\t%\n", get_tokens.size / actual_dur);
		string = string ++ format("tokens_size\t%\n", get_tokens.size);
		string = string ++ format("tokens_generated\t%\n", get_tokens.cs);
		string = string ++ format("tokens_coded_dec:\t%\n", get_tokens_coded.cs);
		string = string ++ format("symbolic_stream:\t%\n", symbolicStream.cs);
		string = string ++ format("counter_data\t%\n", counterOut.cs);
		// write data
		pwd = "/Users/geodia/gitlab/nime2022/data/" ++ dur.asString ++ "sec/";
		pwd.mkdir;
		fn = synthName.asString ++ "_" ++ gesture ++ "_" ++ fs.asString ++ "_" ++ dur.asString ++ "_" ++ low.asString ++ "_" ++ high.asString ++ ".tsv";
		format("fn written: %", fn).postln;
		"---------------------".postln;
		file = File(pwd ++ fn,"w");
		file.write(string);
		file.close;
	}
}