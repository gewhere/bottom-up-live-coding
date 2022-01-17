HuffmanEncoder {

	var <digits;
	var <linear;
	var <>cstate;
	var <>output;
	var <>input;
	var <>states;
	var <>next_state;
	var <dict;

	*new { | data |
		^super.new.init(data)
	}

	init { | data |
		this.transitionsTable(data);
	}

	transitionsTable { | data |
		dict = IdentityDictionary();
		dict[\S0] = [[\S0, \S1], [\A, nil]];
		dict[\S1] = [[\S0, \S2], [\B, nil]];
		dict[\S2] = [[\S0, \S0], [\C, \D]];

		digits = [];
		linear = [];
		data do: { |i|
			digits = digits ++ i[0].asInteger.asBinaryDigits(3);
			linear = linear ++ i[1];
		};
		cstate = \S0;
		output = nil;
		states = [];
		digits do: { | i |
			input = i;
			next_state = dict[cstate][0][input];
			output = dict[cstate][1][input];
			states = states ++ output;
			cstate = next_state;
		};
		^this.states
	}
}