package gus.game5.core.keyboard;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyboardState {
	
	protected Set<Integer> state;
	
	public KeyboardState() {
		state = new HashSet<>();
	}
	
	public void clear() {
		state.clear();
	}
	
	public boolean up() {
		return state.contains(KeyEvent.VK_UP);
	}
	
	public boolean down() {
		return state.contains(KeyEvent.VK_DOWN);
	}
	
	public boolean right() {
		return state.contains(KeyEvent.VK_RIGHT);
	}
	
	public boolean left() {
		return state.contains(KeyEvent.VK_LEFT);
	}
	
	public boolean space() {
		return state.contains(KeyEvent.VK_SPACE);
	}
	
	public boolean escap() {
		return state.contains(KeyEvent.VK_ESCAPE);
	}

	public boolean F1() {
		return state.contains(KeyEvent.VK_F1);
	}
	
	public boolean F2() {
		return state.contains(KeyEvent.VK_F2);
	}
	
	public boolean F3() {
		return state.contains(KeyEvent.VK_F3);
	}
	
	public boolean F4() {
		return state.contains(KeyEvent.VK_F4);
	}
	
	public boolean F5() {
		return state.contains(KeyEvent.VK_F5);
	}
	
	public boolean F6() {
		return state.contains(KeyEvent.VK_F6);
	}
	
	public boolean F7() {
		return state.contains(KeyEvent.VK_F7);
	}
	
	public boolean F8() {
		return state.contains(KeyEvent.VK_F8);
	}
	
	public boolean F9() {
		return state.contains(KeyEvent.VK_F9);
	}
	
	public boolean F10() {
		return state.contains(KeyEvent.VK_F10);
	}
	
	public boolean F11() {
		return state.contains(KeyEvent.VK_F11);
	}
	
	public boolean F12() {
		return state.contains(KeyEvent.VK_F12);
	}
	
	
	public boolean num0() {
		return state.contains(KeyEvent.VK_NUMPAD0);
	}
	
	public boolean num1() {
		return state.contains(KeyEvent.VK_NUMPAD1);
	}
	
	public boolean num2() {
		return state.contains(KeyEvent.VK_NUMPAD2);
	}
	
	public boolean num3() {
		return state.contains(KeyEvent.VK_NUMPAD3);
	}
	
	public boolean num4() {
		return state.contains(KeyEvent.VK_NUMPAD4);
	}
	
	public boolean num5() {
		return state.contains(KeyEvent.VK_NUMPAD5);
	}
	
	public boolean num6() {
		return state.contains(KeyEvent.VK_NUMPAD6);
	}
	
	public boolean num7() {
		return state.contains(KeyEvent.VK_NUMPAD7);
	}
	
	public boolean num8() {
		return state.contains(KeyEvent.VK_NUMPAD8);
	}
	
	public boolean num9() {
		return state.contains(KeyEvent.VK_NUMPAD9);
	}
	
	
	public boolean a() {
		return state.contains(KeyEvent.VK_A);
	}
	
	public boolean b() {
		return state.contains(KeyEvent.VK_B);
	}
	
	public boolean c() {
		return state.contains(KeyEvent.VK_C);
	}
	
	public boolean d() {
		return state.contains(KeyEvent.VK_D);
	}
	
	public boolean e() {
		return state.contains(KeyEvent.VK_E);
	}
	
	public boolean f() {
		return state.contains(KeyEvent.VK_F);
	}
	
	public boolean g() {
		return state.contains(KeyEvent.VK_G);
	}
	
	public boolean h() {
		return state.contains(KeyEvent.VK_H);
	}
	
	public boolean i() {
		return state.contains(KeyEvent.VK_I);
	}
	
	public boolean j() {
		return state.contains(KeyEvent.VK_J);
	}
	
	public boolean k() {
		return state.contains(KeyEvent.VK_K);
	}
	
	public boolean l() {
		return state.contains(KeyEvent.VK_L);
	}
	
	public boolean m() {
		return state.contains(KeyEvent.VK_M);
	}
	
	public boolean n() {
		return state.contains(KeyEvent.VK_N);
	}
	
	public boolean o() {
		return state.contains(KeyEvent.VK_O);
	}
	
	public boolean p() {
		return state.contains(KeyEvent.VK_P);
	}
	
	public boolean q() {
		return state.contains(KeyEvent.VK_Q);
	}
	
	public boolean r() {
		return state.contains(KeyEvent.VK_R);
	}
	
	public boolean s() {
		return state.contains(KeyEvent.VK_S);
	}
	
	public boolean t() {
		return state.contains(KeyEvent.VK_T);
	}
	
	public boolean u() {
		return state.contains(KeyEvent.VK_U);
	}
	
	public boolean v() {
		return state.contains(KeyEvent.VK_V);
	}
	
	public boolean w() {
		return state.contains(KeyEvent.VK_W);
	}
	
	public boolean x() {
		return state.contains(KeyEvent.VK_X);
	}
	
	public boolean y() {
		return state.contains(KeyEvent.VK_Y);
	}
	
	public boolean z() {
		return state.contains(KeyEvent.VK_Z);
	}
}
