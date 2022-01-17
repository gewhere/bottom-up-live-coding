TokenRecognition {

	var <states, <states_str, <states_tokens;
	var <cnt = 0;
	var <cnt2 = 0;
	var <list_of_tokens; // array
	var longestTokens;
	var <all_tokens, <proba, <tokens_sum;
	var <tokensListCoded;

	*new { | states |
		^super.new.init(states)
	}

	init { | states |
		var tmp;
		tmp = states.deepCopy;
		states_str = tmp.join;
		states_str = states_str.replace("A","");
		states_tokens = states_str.split($D); // split tokens on terminal $D (also removes $D)
		states_tokens do: { |elem| if(elem.size > cnt){ cnt = elem.size } };
		// Add $D as terminal -- removed above on the .split() method
		list_of_tokens = [];
		states_tokens.size do: { |i|
			list_of_tokens = list_of_tokens.add(states_tokens[i] ++ "D")
		};
		longestTokens = cnt;
		this.extract(list_of_tokens)
	}

	extract { | list_of_tokens |
		var id_set;
		cnt2 = 0;
		all_tokens = [];

		list_of_tokens do: { | t | all_tokens = all_tokens ++ LHCregexp(t)};
		id_set = all_tokens.as(IdentitySet);
		id_set do: { |symbol| cnt2 = cnt2 + all_tokens.occurrencesOf(symbol) };
		//"\tall_tokes.size = ".post; cnt2.postln; // sanity check -- should be the same size as `states_tokens.size`
		//^this.tokensProba(all_tokens)
	}

	tokensProba { | all_tokens |
		var key;

		proba = IdentityDictionary.new;
		all_tokens.asSet do: { |token| proba[token] = all_tokens.occurrencesOf(token) };
		tokens_sum = sum(proba.values());
		proba do: { |val, idx|
			key = proba.findKeyForValue(val);
			proba.put(key, [val, (val / tokens_sum).round(0.01)]);
		};
		all_tokens = all_tokens.replace(\tokenA, 1);
		all_tokens = all_tokens.replace(\tokenB, 2);
		all_tokens = all_tokens.replace(\tokenC, 3);
		all_tokens = all_tokens.replace(\tokenD, 4);
		all_tokens = all_tokens.replace(\tokenE, 5);
		all_tokens = all_tokens.replace(\tokenF, 6);
		all_tokens = all_tokens.replace(\tokenG, 7);
		//all_tokens.size.postln;
		tokensListCoded =  all_tokens.deepCopy;
	}

}