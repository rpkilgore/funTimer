import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JProgressBar;
import java.awt.Button;

public class happyHrCountdown extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblstatus;
	private JProgressBar jpb;
	private HappyHour hh;
	private JButton okButton;
	private PixelCanvas pc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			happyHrCountdown dialog = new happyHrCountdown();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public happyHrCountdown() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		setIconImage(new ImageIcon("scotch.jpg").getImage());
		setTitle("Fun Time Count Down!");
		setBounds(100, 100, 572, 407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblCountdown = new JLabel("Click Begin To Start");
			lblCountdown.setFont(new Font("Vivaldi", Font.PLAIN, 30));
			lblCountdown.setForeground(Color.WHITE);
			lblCountdown.setBackground(Color.BLACK);
			lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblCountdown, BorderLayout.NORTH);
			lblstatus = lblCountdown;
		}
		{
			jpb = new JProgressBar();
			jpb.setStringPainted(true);
			contentPanel.add(jpb, BorderLayout.SOUTH);
		}
		{
			pc = new PixelCanvas();
			contentPanel.add(pc, BorderLayout.CENTER);
		}
//		{
//			pc = new PixelCanvas();
//			contentPanel.add(pc, BorderLayout.CENTER);
//		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Begin");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						initialize();
						startTimer(lblstatus);
						// disable the start button..
						okButton.setEnabled(false);
					}
				});
				{
					JButton btnConfigure = new JButton("Configure");
					btnConfigure.setEnabled(false);
					btnConfigure.setVisible(false);
					btnConfigure.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//TODO Pop up box to configure options
						}
					});
					{
						JButton btnTestbutton = new JButton("ImageLoadTest");
						btnTestbutton.setEnabled(false);
						btnTestbutton.setVisible(false);
						btnTestbutton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								// just a testing button
								// lets try to load up an image first..
								BufferedImage img = null;
								try {
								    img = ImageIO.read(new File("Fun_Time.jpg"));
								} catch (IOException e) {
								    e.printStackTrace();
								}
								System.out.println("width: " + pc.getWidth() + " h: " + pc.getHeight());
								// now resize it?
								Image dimg = img.getScaledInstance(pc.getWidth(), pc.getHeight(),
								        Image.SCALE_SMOOTH);
								// set it in our canvas... but don't refresh? cause we will do that later.. :)
								pc.setImage(dimg);

								//pc.repaint();
								//ImageIcon imageIcon = new ImageIcon(dimg);
								//lblImage.setIcon(imageIcon);
								
							}
						});
						{
							Button button = new Button("test2");
							button.setEnabled(false);
							button.setVisible(false);
							button.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									pc.updateImage(5);
								}
							});
							buttonPane.add(button);
						}
						buttonPane.add(btnTestbutton);
					}
					buttonPane.add(btnConfigure);
				}
				okButton.setActionCommand("Begin");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Stop");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						okButton.setEnabled(true);
						stopTimer();
					}
				});
				cancelButton.setActionCommand("Stop");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void initialize(){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("Fun_Time.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		// now resize it?
		Image dimg = img.getScaledInstance(pc.getWidth(), pc.getHeight(),
		        Image.SCALE_SMOOTH);
		// set it in our canvas... but don't refresh? cause we will do that later.. :)
		pc.setImage(dimg);
	}
	
	public void stopTimer(){
		if(hh!=null)
			hh.setStopCountDown(true);
	}
	
	public void startTimer(JLabel statusMsg){
		new Thread(new Runnable() {
			public void run() {
				try {
					// Kick off our new timer
					hh = new HappyHour(statusMsg, jpb, pc);
					// set custom settings
//					hh.setDuring_happyhr(during_happyhr);
//					hh.setHappyHr_duration_hrs(0);
//					hh.setHappyHr_duration_min(2);
					hh.setHappyhr_time("22:51:00");
//					hh.setDuring_happyhr(during_happyhr);
					hh.setPrior_happyhr("till Reception Inception");
					hh.doWork();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();;
	}
}
