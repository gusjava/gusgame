package gus.game5.core.util;

public class UtilDiacritics {
	
	public static String normalizeU(String s) {
		if(s==null) return null;
		return normalize(s.trim()).toUpperCase().replace(' ','_');
	}

	public static String normalize(String s) {
		if(s==null) return null;
		
		int l = s.length();
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < l; i++)
			handle(b, s.charAt(i));
		return b.toString();
	}

	private static void handle(StringBuilder b, char c) {
		switch (c) {
			// uppercase

			case 'A':
				b.append('a');
				break;// A
			case 'B':
				b.append('b');
				break;// B
			case 'C':
				b.append('c');
				break;// C
			case 'D':
				b.append('d');
				break;// D
			case 'E':
				b.append('e');
				break;// E
			case 'F':
				b.append('f');
				break;// F
			case 'G':
				b.append('g');
				break;// G
			case 'H':
				b.append('h');
				break;// H
			case 'I':
				b.append('i');
				break;// I
			case 'J':
				b.append('j');
				break;// J
			case 'K':
				b.append('k');
				break;// K
			case 'L':
				b.append('l');
				break;// L
			case 'M':
				b.append('m');
				break;// M
			case 'N':
				b.append('n');
				break;// N
			case 'O':
				b.append('o');
				break;// O
			case 'P':
				b.append('p');
				break;// P
			case 'Q':
				b.append('q');
				break;// Q
			case 'R':
				b.append('r');
				break;// R
			case 'S':
				b.append('s');
				break;// S
			case 'T':
				b.append('t');
				break;// T
			case 'U':
				b.append('u');
				break;// U
			case 'V':
				b.append('v');
				break;// V
			case 'W':
				b.append('w');
				break;// W
			case 'X':
				b.append('x');
				break;// x
			case 'Y':
				b.append('y');
				break;// Y
			case 'Z':
				b.append('z');
				break;// Z

			// lowercase diacritics

//			case 'à':
//				b.append('a');
//				break;// à accent grave
//			case 'á':
//				b.append('a');
//				break;// á accent aigu
//			case 'â':
//				b.append('a');
//				break;// â accent circonflexe
//			case 'ã':
//				b.append('a');
//				break;// ã tilde
//			case 'ä':
//				b.append('a');
//				break;// ä tréma
//			case 'å':
//				b.append('a');
//				break;// å rond en chef
//			case '�?':
//				b.append('a');
//				break;// a macron (accent plat)
//			case 'ă':
//				b.append('a');
//				break;// a brève (accent goutte)
//			case 'ą':
//				b.append('a');
//				break;// a ogonek (petite queue)
//			case 'ạ':
//				b.append('a');
//				break;// a point souscrit
//			case 'ǎ':
//				b.append('a');
//				break;// a hatchek (accent hirondelle)
//			case 'ȧ':
//				b.append('a');
//				break;// a point suscrit
//
//			case 'æ':
//				b.append("ae");
//				break;// æ
//			case 'ǽ':
//				b.append("ae");
//				break;// æ accent aigu
//			case 'ǣ':
//				b.append("ae");
//				break;// æ macron
//
//			case 'ḃ':
//				b.append('b');
//				break;// b point suscrit
//			case 'ḅ':
//				b.append('b');
//				break;// b point souscrit
//			case 'ɓ':
//				b.append('b');
//				break;// b crochet
//
//			case 'ç':
//				b.append('c');
//				break;// ç cédille
//			case 'ć':
//				b.append('c');
//				break;// c accent aigu
//			case 'ĉ':
//				b.append('c');
//				break;// c accent circonflexe
//			case 'ċ':
//				b.append('c');
//				break;// c point suscrit
//			case '�?':
//				b.append('c');
//				break;// c hatchek
//			case 'ƈ':
//				b.append('c');
//				break;// c crochet
//			case 'ð':
//				b.append('c');
//				break;// ð barre
//
//			case '�?':
//				b.append('d');
//				break;// d hatchek
//			case 'ɗ':
//				b.append('d');
//				break;// d crochet
//			case 'ḋ':
//				b.append('d');
//				break;// d point suscrit
//			case '�?':
//				b.append('d');
//				break;// d point souscrit
//			case 'ḑ':
//				b.append('d');
//				break;// d cédille
//
//			case 'è':
//				b.append('e');
//				break;// è accent grave
//			case 'é':
//				b.append('e');
//				break;// é accent aigu
//			case 'ê':
//				b.append('e');
//				break;// ê accent circonflexe
//			case 'ë':
//				b.append('e');
//				break;// ë tréma
//			case 'ē':
//				b.append('e');
//				break;// e macron
//			case 'ĕ':
//				b.append('e');
//				break;// e brève
//			case 'ė':
//				b.append('e');
//				break;// e point suscrit
//			case 'ę':
//				b.append('e');
//				break;// e ogonek
//			case 'ě':
//				b.append('e');
//				break;// e hatchek
//			case 'ȩ':
//				b.append('e');
//				break;// e cédille
//			case 'ẹ':
//				b.append('e');
//				break;// e point souscrit
//			case 'ẽ':
//				b.append('e');
//				break;// e tilde
//
//			case 'ḟ':
//				b.append('f');
//				break;// f point suscrit
//
//			case '�?':
//				b.append('g');
//				break;// g accent circonflexe
//			case 'ğ':
//				b.append('g');
//				break;// g brève
//			case 'ġ':
//				b.append('g');
//				break;// g point suscrit
//			case 'ģ':
//				b.append('g');
//				break;// g cédille
//			case 'ǥ':
//				b.append('g');
//				break;// g barre
//			case 'ǧ':
//				b.append('g');
//				break;// g hatchek
//			case 'ɠ':
//				b.append('g');
//				break;// g crochet
//			case 'ǵ':
//				b.append('g');
//				break;// g accent aigu
//			case 'ḡ':
//				b.append('g');
//				break;// g macron
//
//			case 'ĥ':
//				b.append('h');
//				break;// h accent circonflexe
//			case 'ħ':
//				b.append('h');
//				break;// h barre
//			case 'ḣ':
//				b.append('h');
//				break;// h point suscrit
//			case 'ḥ':
//				b.append('h');
//				break;// h point souscrit
//			case 'ḧ':
//				b.append('h');
//				break;// h tréma
//			case 'ḩ':
//				b.append('h');
//				break;// h cédille
//			case 'ȟ':
//				b.append('h');
//				break;// h hatchek
//
//			case 'ì':
//				b.append('i');
//				break;// ì accent grave
//			case 'í':
//				b.append('i');
//				break;// í accent aigu
//			case 'î':
//				b.append('i');
//				break;// î accent circonflexe
//			case 'ï':
//				b.append('i');
//				break;// ï tréma
//			case '�?':
//				b.append('i');
//				break;// i hatchek
//			case 'ĩ':
//				b.append('i');
//				break;// i tilde
//			case 'ī':
//				b.append('i');
//				break;// i macron
//			case 'ĭ':
//				b.append('i');
//				break;// i brève
//			case 'į':
//				b.append('i');
//				break;// i ogonek
//			case 'ị':
//				b.append('i');
//				break;// i point souscrit
//
//			case 'ĵ':
//				b.append('j');
//				break;// j accent circonflexe
//			case 'ǰ':
//				b.append('j');
//				break;// j hatchek
//
//			case 'ḱ':
//				b.append('k');
//				break;// k accent aigu
//			case 'ḳ':
//				b.append('k');
//				break;// k point souscrit
//			case 'ķ':
//				b.append('k');
//				break;// k cédille
//			case 'ƙ':
//				b.append('k');
//				break;// k crochet
//			case 'ǩ':
//				b.append('k');
//				break;// k hatchek
//
//			case 'ḷ':
//				b.append('l');
//				break;// l point souscrit
//			case 'ĺ':
//				b.append('l');
//				break;// l accent aigu
//			case 'ļ':
//				b.append('l');
//				break;// l cédille
//			case 'ľ':
//				b.append('l');
//				break;// l hatchek
//			case 'ł':
//				b.append('l');
//				break;// l barre
//
//			case 'ḿ':
//				b.append('m');
//				break;// m accent aigu
//			case '�?':
//				b.append('m');
//				break;// m point suscrit
//			case 'ṃ':
//				b.append('m');
//				break;// m point souscrit
//
//			case 'ṅ':
//				b.append('n');
//				break;// n point suscrit
//			case 'ṇ':
//				b.append('n');
//				break;// n point souscrit
//			case 'ǹ':
//				b.append('n');
//				break;// n accent grave
//			case 'ñ':
//				b.append('n');
//				break;// ñ tilde
//			case 'ń':
//				b.append('n');
//				break;// n accent aigu
//			case 'ņ':
//				b.append('n');
//				break;// n cédille
//			case 'ň':
//				b.append('n');
//				break;// n hatchek
//
//			case 'ò':
//				b.append('o');
//				break;// ò accent grave
//			case 'ó':
//				b.append('o');
//				break;// ó accent aigu
//			case 'ô':
//				b.append('o');
//				break;// ô accent circonflexe
//			case 'õ':
//				b.append('o');
//				break;// õ tilde
//			case 'ö':
//				b.append('o');
//				break;// ö tréma
//			case 'ø':
//				b.append('o');
//				break;// ø barre
//			case '�?':
//				b.append('o');
//				break;// o macron
//			case '�?':
//				b.append('o');
//				break;// o brève
//			case 'ő':
//				b.append('o');
//				break;// o double accent aigu
//			case 'ơ':
//				b.append('o');
//				break;// o corne
//			case 'ǒ':
//				b.append('o');
//				break;// o hatchek
//			case 'ǫ':
//				b.append('o');
//				break;// o ogonek
//			case 'ȯ':
//				b.append('o');
//				break;// o point suscrit
//			case '�?':
//				b.append('o');
//				break;// o point souscrit
//
//			case 'œ':
//				b.append("oe");
//				break;// œ
//
//			case 'ṕ':
//				b.append('p');
//				break;// p accent aigu
//			case 'ṗ':
//				b.append('p');
//				break;// p point suscrit
//			case 'ƥ':
//				b.append('p');
//				break;// p crochet
//
//			case 'ṙ':
//				b.append('r');
//				break;// r point suscrit
//			case 'ṛ':
//				b.append('r');
//				break;// r point souscrit
//			case 'ŕ':
//				b.append('r');
//				break;// r accent aigu
//			case 'ŗ':
//				b.append('r');
//				break;// r cédille
//			case 'ř':
//				b.append('r');
//				break;// r hatchek
//
//			case 'ṡ':
//				b.append('s');
//				break;// session_ point suscrit
//			case 'ṣ':
//				b.append('s');
//				break;// session_ point souscrit
//			case 'ś':
//				b.append('s');
//				break;// session_ accent aigu
//			case '�?':
//				b.append('s');
//				break;// session_ accent circonflexe
//			case 'ş':
//				b.append('s');
//				break;// session_ cédille
//			case 'š':
//				b.append('s');
//				break;// š hatchek
//			case 'ș':
//				b.append('s');
//				break;// session_ virgule
//
//			case 'ṫ':
//				b.append('t');
//				break;// t point suscrit
//			case 'ṭ':
//				b.append('t');
//				break;// t point souscrit
//			case 'ţ':
//				b.append('t');
//				break;// t cédille
//			case 'ť':
//				b.append('t');
//				break;// t hatchek
//			case 'ŧ':
//				b.append('t');
//				break;// t barre
//			case 'ƭ':
//				b.append('t');
//				break;// t crochet
//			case 'ț':
//				b.append('t');
//				break;// t virgule
//			case 'ẗ':
//				b.append('t');
//				break;// t tréma
//
//			case 'ụ':
//				b.append('u');
//				break;// u point souscrit
//			case 'ù':
//				b.append('u');
//				break;// ù accent grave
//			case 'ú':
//				b.append('u');
//				break;// ú accent aigu
//			case 'û':
//				b.append('u');
//				break;// û accent circonflexe
//			case 'ü':
//				b.append('u');
//				break;// ü tréma
//			case 'ũ':
//				b.append('u');
//				break;// u tilde
//			case 'ū':
//				b.append('u');
//				break;// u macron
//			case 'ŭ':
//				b.append('u');
//				break;// u brève
//			case 'ů':
//				b.append('u');
//				break;// u rond en chef
//			case 'ű':
//				b.append('u');
//				break;// u double accent aigu
//			case 'ų':
//				b.append('u');
//				break;// u ogonek
//			case 'ư':
//				b.append('u');
//				break;// u corne
//			case 'ǔ':
//				b.append('u');
//				break;// u hatchek
//
//			case 'ṽ':
//				b.append('v');
//				break;// v tilde
//			case 'ṿ':
//				b.append('v');
//				break;// v point souscrit
//
//			case 'ŵ':
//				b.append('w');
//				break;// w accent circonflexe
//			case '�?':
//				b.append('w');
//				break;// w accent grave
//			case 'ẃ':
//				b.append('w');
//				break;// w accent aigu
//			case 'ẅ':
//				b.append('w');
//				break;// w tréma
//			case 'ẇ':
//				b.append('w');
//				break;// w point suscrit
//			case 'ẉ':
//				b.append('w');
//				break;// w point souscrit
//			case 'ẘ':
//				b.append('w');
//				break;// w rond en chef
//
//			case 'ẋ':
//				b.append('x');
//				break;// x point suscrit
//			case '�?':
//				b.append('x');
//				break;// x tréma
//
//			case 'ŷ':
//				b.append('y');
//				break;// y accent circonflexe
//			case 'ÿ':
//				b.append('y');
//				break;// ÿ tréma
//			case 'ý':
//				b.append('y');
//				break;// ý accent aigu
//			case '�?':
//				b.append('y');
//				break;// y point suscrit
//			case 'ƴ':
//				b.append('y');
//				break;// y crochet
//			case 'ȳ':
//				b.append('y');
//				break;// y macron
//			case 'ỵ':
//				b.append('y');
//				break;// y point souscrit
//			case 'ỹ':
//				b.append('y');
//				break;// y tilde
//			case 'ỳ':
//				b.append('y');
//				break;// y accent grave
//			case 'ẙ':
//				b.append('y');
//				break;// y rond en chef
//
//			case 'ź':
//				b.append('z');
//				break;// z accent aigu
//			case 'ż':
//				b.append('z');
//				break;// z point suscrit
//			case 'ž':
//				b.append('z');
//				break;// ž hatchek
//			case 'ƶ':
//				b.append('z');
//				break;// z barre
//			case 'ẑ':
//				b.append('z');
//				break;// z accent circonflexe
//			case 'ẓ':
//				b.append('z');
//				break;// z point souscrit
//
//			// uppercase diacritics
//
//			case 'À':
//				b.append('a');
//				break;// À accent grave
//			case '�?':
//				b.append('a');
//				break;// �? accent aigu
//			case 'Â':
//				b.append('a');
//				break;// Â accent circonflexe
//			case 'Ã':
//				b.append('a');
//				break;// Ã tilde
//			case 'Ä':
//				b.append('a');
//				break;// Ä tréma
//			case 'Å':
//				b.append('a');
//				break;// Å rond en chef
//			case 'Ā':
//				b.append('a');
//				break;// A macron
//			case 'Ă':
//				b.append('a');
//				break;// A brève
//			case 'Ą':
//				b.append('a');
//				break;// A ogonek
//			case 'Ạ':
//				b.append('a');
//				break;// A point souscrit
//			case '�?':
//				b.append('a');
//				break;// A hatchek
//			case 'Ȧ':
//				b.append('a');
//				break;// A point suscrit
//
//			case 'Æ':
//				b.append("ae");
//				break;// Æ
//			case 'Ǽ':
//				b.append("ae");
//				break;// Æ accent aigu
//			case 'Ǣ':
//				b.append("ae");
//				break;// Æ macron
//
//			case 'Ƀ':
//				b.append('b');
//				break;// B barre
//			case 'Ḃ':
//				b.append('b');
//				break;// B point suscrit
//			case 'Ḅ':
//				b.append('b');
//				break;// B point souscrit
//			case '�?':
//				b.append('b');
//				break;// B crochet
//
//			case 'Ç':
//				b.append('c');
//				break;// Ç cédille
//			case 'Ć':
//				b.append('c');
//				break;// C accent aigu
//			case 'Ĉ':
//				b.append('c');
//				break;// C accent circonflexe
//			case 'Ċ':
//				b.append('c');
//				break;// C point suscrit
//			case 'Č':
//				b.append('c');
//				break;// C hatchek
//			case 'Ƈ':
//				b.append('c');
//				break;// C crochet
//
//			case '�?':
//				b.append('d');
//				break;// D barre
//			case 'Ď':
//				b.append('d');
//				break;// D hatchek
//			case 'Ɗ':
//				b.append('d');
//				break;// D crochet
//			case 'Ḋ':
//				b.append('d');
//				break;// D point suscrit
//			case 'Ḍ':
//				b.append('d');
//				break;// D point souscrit
//			case '�?':
//				b.append('d');
//				break;// D cédille
//
//			case 'È':
//				b.append('e');
//				break;// È accent grave
//			case 'É':
//				b.append('e');
//				break;// É accent aigu
//			case 'Ê':
//				b.append('e');
//				break;// Ê accent circonflexe
//			case 'Ë':
//				b.append('e');
//				break;// Ë tréma
//			case 'Ē':
//				b.append('e');
//				break;// E macron
//			case 'Ĕ':
//				b.append('e');
//				break;// E brève
//			case 'Ė':
//				b.append('e');
//				break;// E point suscrit
//			case 'Ę':
//				b.append('e');
//				break;// E ogonek
//			case 'Ě':
//				b.append('e');
//				break;// E hatchek
//			case 'Ȩ':
//				b.append('e');
//				break;// E cédille
//			case 'Ẹ':
//				b.append('e');
//				break;// E point souscrit
//			case 'Ẽ':
//				b.append('e');
//				break;// E tilde
//
//			case 'Ḟ':
//				b.append('f');
//				break;// F point suscrit
//
//			case 'Ĝ':
//				b.append('g');
//				break;// G accent circonflexe
//			case 'Ğ':
//				b.append('g');
//				break;// G brève
//			case 'Ġ':
//				b.append('g');
//				break;// G point suscrit
//			case 'Ģ':
//				b.append('g');
//				break;// G cédille
//			case 'Ǥ':
//				b.append('g');
//				break;// G barre
//			case 'Ǧ':
//				b.append('g');
//				break;// G hatchek
//			case 'Ɠ':
//				b.append('g');
//				break;// G crochet
//			case 'Ǵ':
//				b.append('g');
//				break;// G accent aigu
//			case 'Ḡ':
//				b.append('g');
//				break;// G macron
//
//			case 'Ĥ':
//				b.append('h');
//				break;// H accent circonflexe
//			case 'Ħ':
//				b.append('h');
//				break;// H barre
//			case 'Ḣ':
//				b.append('h');
//				break;// H point suscrit
//			case 'Ḥ':
//				b.append('h');
//				break;// H point souscrit
//			case 'Ḧ':
//				b.append('h');
//				break;// H tréma
//			case 'Ḩ':
//				b.append('h');
//				break;// H cédille
//			case 'Ȟ':
//				b.append('h');
//				break;// H hatchek
//
//			case 'Ì':
//				b.append('i');
//				break;// Ì accent grave
//			case '�?':
//				b.append('i');
//				break;// �? accent aigu
//			case 'Î':
//				b.append('i');
//				break;// Î accent circonflexe
//			case '�?':
//				b.append('i');
//				break;// �? tréma
//			case '�?':
//				b.append('i');
//				break;// I hatchek
//			case 'Ĩ':
//				b.append('i');
//				break;// I tilde
//			case 'Ī':
//				b.append('i');
//				break;// I macron
//			case 'Ĭ':
//				b.append('i');
//				break;// I brève
//			case 'Į':
//				b.append('i');
//				break;// I ogonek
//			case 'İ':
//				b.append('i');
//				break;// I point suscrit
//			case 'Ị':
//				b.append('i');
//				break;// I point souscrit
//
//			case 'Ĵ':
//				b.append('j');
//				break;// J accent circonflexe
//
//			case 'Ḱ':
//				b.append('k');
//				break;// K accent aigu
//			case 'Ḳ':
//				b.append('k');
//				break;// K point souscrit
//			case 'Ķ':
//				b.append('k');
//				break;// K cédille
//			case 'Ƙ':
//				b.append('k');
//				break;// K crochet
//			case 'Ǩ':
//				b.append('k');
//				break;// K hatchek
//
//			case 'Ḷ':
//				b.append('l');
//				break;// L point souscrit
//			case 'Ĺ':
//				b.append('l');
//				break;// L accent aigu
//			case 'Ļ':
//				b.append('l');
//				break;// L cédille
//			case 'Ľ':
//				b.append('l');
//				break;// L hatchek
//			case '�?':
//				b.append('l');
//				break;// L barre
//
//			case 'Ḿ':
//				b.append('m');
//				break;// M accent aigu
//			case 'Ṁ':
//				b.append('m');
//				break;// M point suscrit
//			case 'Ṃ':
//				b.append('m');
//				break;// M point souscrit
//
//			case 'Ṅ':
//				b.append('n');
//				break;// N point suscrit
//			case 'Ṇ':
//				b.append('n');
//				break;// N point souscrit
//			case 'Ǹ':
//				b.append('n');
//				break;// N accent grave
//			case 'Ñ':
//				b.append('n');
//				break;// N tilde
//			case 'Ń':
//				b.append('n');
//				break;// N accent aigu
//			case 'Ņ':
//				b.append('n');
//				break;// N cédille
//			case 'Ň':
//				b.append('n');
//				break;// N hatchek
//
//			case 'Ò':
//				b.append('o');
//				break;// Ò accent grave
//			case 'Ó':
//				b.append('o');
//				break;// Ó accent aigu
//			case 'Ô':
//				b.append('o');
//				break;// Ô accent circonflexe
//			case 'Õ':
//				b.append('o');
//				break;// Õ tilde
//			case 'Ö':
//				b.append('o');
//				break;// Ö tréma
//			case 'Ø':
//				b.append('o');
//				break;// Ø barre
//			case 'Ō':
//				b.append('o');
//				break;// O macron
//			case 'Ŏ':
//				b.append('o');
//				break;// O brève
//			case '�?':
//				b.append('o');
//				break;// O double accent aigu
//			case 'Ơ':
//				b.append('o');
//				break;// O corne
//			case 'Ǒ':
//				b.append('o');
//				break;// O hatchek
//			case 'Ǫ':
//				b.append('o');
//				break;// O ogonek
//			case 'Ȯ':
//				b.append('o');
//				break;// O point suscrit
//			case 'Ọ':
//				b.append('o');
//				break;// O point souscrit
//
//			case 'Œ':
//				b.append("oe");
//				break;// Œ
//
//			case 'Ṕ':
//				b.append('p');
//				break;// P accent aigu
//			case 'Ṗ':
//				b.append('p');
//				break;// P point suscrit
//			case 'Ƥ':
//				b.append('p');
//				break;// P crochet
//
//			case 'Ṙ':
//				b.append('r');
//				break;// R point suscrit
//			case 'Ṛ':
//				b.append('r');
//				break;// R point souscrit
//			case 'Ŕ':
//				b.append('r');
//				break;// R accent aigu
//			case 'Ŗ':
//				b.append('r');
//				break;// R cédille
//			case 'Ř':
//				b.append('r');
//				break;// R hatchek
//
//			case 'Ṡ':
//				b.append('s');
//				break;// S point suscrit
//			case 'Ṣ':
//				b.append('s');
//				break;// S point souscrit
//			case 'Ś':
//				b.append('s');
//				break;// S accent aigu
//			case 'Ŝ':
//				b.append('s');
//				break;// S accent circonflexe
//			case 'Ş':
//				b.append('s');
//				break;// S cédille
//			case 'Š':
//				b.append('s');
//				break;// S hatchek
//			case 'Ș':
//				b.append('s');
//				break;// S virgule
//
//			case 'Ṫ':
//				b.append('t');
//				break;// T point suscrit
//			case 'Ṭ':
//				b.append('t');
//				break;// T point souscrit
//			case 'Ţ':
//				b.append('t');
//				break;// T cédille
//			case 'Ť':
//				b.append('t');
//				break;// T hatchek
//			case 'Ŧ':
//				b.append('t');
//				break;// T barre
//			case 'Ƭ':
//				b.append('t');
//				break;// T crochet
//			case 'Ț':
//				b.append('t');
//				break;// T virgule
//
//			case 'Ụ':
//				b.append('u');
//				break;// U point souscrit
//			case 'Ù':
//				b.append('u');
//				break;// Ù accent grave
//			case 'Ú':
//				b.append('u');
//				break;// Ú accent aigu
//			case 'Û':
//				b.append('u');
//				break;// Û accent circonflexe
//			case 'Ü':
//				b.append('u');
//				break;// Ü tréma
//			case 'Ũ':
//				b.append('u');
//				break;// U tilde
//			case 'Ū':
//				b.append('u');
//				break;// U macron
//			case 'Ŭ':
//				b.append('u');
//				break;// U brève
//			case 'Ů':
//				b.append('u');
//				break;// U rond en chef
//			case 'Ű':
//				b.append('u');
//				break;// U double accent aigu
//			case 'Ų':
//				b.append('u');
//				break;// U ogonek
//			case 'Ư':
//				b.append('u');
//				break;// U corne
//			case 'Ǔ':
//				b.append('u');
//				break;// U hatchek
//
//			case 'Ṽ':
//				b.append('v');
//				break;// V tilde
//			case 'Ṿ':
//				b.append('v');
//				break;// V point souscrit
//
//			case 'Ŵ':
//				b.append('w');
//				break;// W accent circonflexe
//			case 'Ẁ':
//				b.append('w');
//				break;// W accent grave
//			case 'Ẃ':
//				b.append('w');
//				break;// W accent aigu
//			case 'Ẅ':
//				b.append('w');
//				break;// W tréma
//			case 'Ẇ':
//				b.append('w');
//				break;// W point suscrit
//			case 'Ẉ':
//				b.append('w');
//				break;// W point souscrit
//
//			case 'Ẋ':
//				b.append('x');
//				break;// X point suscrit
//			case 'Ẍ':
//				b.append('x');
//				break;// X tréma
//
//			case 'Ŷ':
//				b.append('y');
//				break;// Y accent circonflexe
//			case 'Ÿ':
//				b.append('y');
//				break;// Ÿ tréma
//			case '�?':
//				b.append('y');
//				break;// �? accent aigu
//			case 'Ẏ':
//				b.append('y');
//				break;// Y point suscrit
//			case 'Ƴ':
//				b.append('y');
//				break;// Y crochet
//			case 'Ȳ':
//				b.append('y');
//				break;// Y macron
//			case 'Ỵ':
//				b.append('y');
//				break;// Y point souscrit
//			case 'Ỹ':
//				b.append('y');
//				break;// Y tilde
//			case 'Ỳ':
//				b.append('y');
//				break;// Y accent grave
//
//			case 'Ź':
//				b.append('z');
//				break;// Z accent aigu
//			case 'Ż':
//				b.append('z');
//				break;// Z point suscrit
//			case 'Ž':
//				b.append('z');
//				break;// Z hatchek
//			case 'Ƶ':
//				b.append('z');
//				break;// Z barre
//			case '�?':
//				b.append('z');
//				break;// Z accent circonflexe
//			case 'Ẓ':
//				b.append('z');
//				break;// Z point souscrit

			default:
				b.append(c);
		}
	}
}