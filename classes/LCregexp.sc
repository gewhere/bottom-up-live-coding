LCregexp {
	var <>list;

	*new { | str |
		^super.new.init(str);
	}

	init { | str |
		this.tokenList;
		^this.detectRegExp(str);
	}

	tokenList {
		list = ["^D", "^B+D", "^C+D", "^(B+C+)+D", "^(C+B+)+D", "^(B+C+)+B+D", "^(C+B+)+C+D"];
	}

	detectRegExp { | str |
		case
		{ list[0].matchRegexp(str) } { ^\tokenA }
		{ list[1].matchRegexp(str) } { ^\tokenB }
		{ list[2].matchRegexp(str) } { ^\tokenC }
		{ list[3].matchRegexp(str) } { ^\tokenD }
		{ list[4].matchRegexp(str) } { ^\tokenE }
		{ list[5].matchRegexp(str) } { ^\tokenF }
		{ list[6].matchRegexp(str) } { ^\tokenG };
	}

}
/*

	LCregexp("CD")

*/
