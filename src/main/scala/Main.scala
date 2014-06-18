/**
 * Created by masaakif on 2014/06/18.
 */

package covernotice

import java.awt.event.WindowEvent


object Main extends App {
	val n = new Notice
	val s = if (args.length < 1) "SAMPLE STRING" else args(0)
	n.setString(s)
	n.show
	n.click
}

import java.awt.{ Frame, Dimension, Color, Label, Font, Robot}
import java.awt.event.{WindowAdapter, WindowEvent}

object MyEvent extends WindowAdapter {
	override def windowClosing(e: WindowEvent): Unit = e.getWindow.dispose()
}

trait MouseClicker {
	import java.awt.Robot
	import java.awt.event.InputEvent
	def click = {
		new Robot{
			mousePress(InputEvent.BUTTON1_MASK)
			mouseRelease(InputEvent.BUTTON1_MASK)}
	}
}

trait EnvironmentGetter {
	import java.awt.{ GraphicsEnvironment => GE, Rectangle}
	def getDesktopSize:Rectangle = {
		val gs = GE.getLocalGraphicsEnvironment.getScreenDevices
		val bs = for(d <- gs; gc = d.getConfigurations; c <- gc) yield c.getBounds
		((new Rectangle) /: bs){(res,b) => res.union(b)}
	}
}
class Notice extends WindowAdapter with EnvironmentGetter with MouseClicker{
	val (w, h) = (getDesktopSize.getWidth.toInt, getDesktopSize.getHeight.toInt)
	private val frame = new Frame {
		val d = new Dimension(w, h)
		setMinimumSize(d)
		setAlwaysOnTop(true)
		addWindowListener(MyEvent)
		setBackground(Color.YELLOW)
	}
	def show:Unit = {
		frame.setVisible(true)
	}

	def setString(s:String):Unit = {
		val l = new Label(s)
		l.setFont(new Font("Arial", Font.BOLD, 345))
		frame.add(l)
	}
}

