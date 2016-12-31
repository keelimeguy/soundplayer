package SoundPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Example {

	public static SoundPlayer snd = new SoundPlayer();

	public static void main(String[] args) {

		snd.start();

		JFrame frame = new JFrame("Test");
		frame.setVisible(true);
		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);

		JButton button = new JButton("child");
		panel.add(button);
		button.addActionListener(new Action1());

		JButton button2 = new JButton("epic");
		panel.add(button2);
		button2.addActionListener(new Action2());

		JButton button3 = new JButton("funky");
		panel.add(button3);
		button3.addActionListener(new Action3());

		JButton button4 = new JButton("funky_variation");
		panel.add(button4);
		button4.addActionListener(new Action4());

		JButton button5 = new JButton("spooky");
		panel.add(button5);
		button5.addActionListener(new Action5());
	}

	static class Action1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			/*JFrame frame2 = new JFrame("child");
			frame2.setVisible(true);
			frame2.setSize(200, 200);
			 */
			snd.reset();
			snd.playMusic("/res/child.wav", (int) (44100.0 * 9.0), -1);
		}
	}

	static class Action2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			snd.reset();
			snd.playMusic("/res/epic.wav", (int) (44100.0 * 7.8), -1);
		}
	}

	static class Action3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			snd.reset();
			snd.playMusic("/res/funky.wav", (int) (44100.0 * 32.77), -1);
		}
	}

	static class Action4 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			snd.reset();
			snd.playMusic("/res/varied_funky.wav", (int) (44100.0 * 8.18), -1);
		}
	}

	static class Action5 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			snd.reset();
			snd.playMusic("/res/spooky.wav");
		}
	}
}