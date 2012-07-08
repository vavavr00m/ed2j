package ed2k.server;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JOptionPane;

import ed2k.server.conn.AcceptTCP;
import ed2k.server.conn.DefaultConnFactory;
import ed2k.server.core.Manager;

public class Ed2JServerTary {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		AcceptTCP atcp = null;
		int port = 4661;
		while (atcp == null) {
			try {
				atcp = new AcceptTCP(port);
			} catch (IOException e1) {
				String str = JOptionPane.showInputDialog(null, "端口" + port + "已被占用。\n请输入端口号：");
				try {
					port = Integer.parseInt(str);
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
		atcp.setFactory(new DefaultConnFactory());
		new Thread(atcp).start();
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.setColor(Color.green);
			g.fillOval(0, 0, 48, 48);
			PopupMenu popup = new PopupMenu();
			MenuItem exitMenuItem = new MenuItem("退出");
			exitMenuItem.addActionListener(new MyActionListener(atcp));
			popup.add(exitMenuItem);
			final TrayIcon trayIcon = new TrayIcon(image, "Ed2J Server", popup);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					StringBuilder sb = new StringBuilder();
					sb.append("客户端数:");
					sb.append(Manager.clientCount());
					sb.append('\n');
					sb.append("文件数:");
					sb.append(Manager.fileCount());
					trayIcon.displayMessage("状态", sb.toString(), MessageType.NONE);
				}
			});
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	static class MyActionListener implements ActionListener {
		AcceptTCP atcp;

		public MyActionListener(AcceptTCP atcp) {
			super();
			this.atcp = atcp;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				atcp.close();
			} catch (IOException e1) {
			}
			System.exit(0);
		}

	}
}
