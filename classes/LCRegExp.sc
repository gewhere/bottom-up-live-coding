LCRegExp {

	var regExp;

	*new { | str |
		^super.new.init(str);
	}

	init { | str |
		this.initRegexp;
		this.detectRegExp(str);
	}

	initRegexp {
		regExp = "^D|^B+D|^C+D|^(B+C+)+D|^(C+B+)+D|^(B+C+)+B+D|^(C+B+)+C+D";

	}

	detectRegExp { | str |
		if( regExp.matchRegexp(str, 0, str.size) == true ) {
			str.post; "\t (accepted)".postln;
		}
	}

}
