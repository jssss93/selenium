import win32api
import win32gui
import win32con
import win32ui

#hinstance = win32api.GetModuleHandle(None)

hWnd = win32gui.GetDesktopWindow()
# Retrieves a handle to the desktop window. The desktop window covers the entire screen.
# The desktop window is the area on top of which other windows are painted.
hdc = win32gui.GetDC(hWnd)
#win32gui.GetDC(None)
# A handle to the window whose DC is to be retrieved. If this value is NULL, GetDC
# retrieves the DC for the entire screen.

hMemDC = win32gui.CreateCompatibleDC(hdc)

hImage = win32gui.LoadImage(None, "./test.png", win32con.IMAGE_BITMAP, 0, 0, win32con.LR_LOADFROMFILE | win32con.LR_CREATEDIBSECTION);
# Loads an icon, cursor, animated cursor, or bitmap

hOldBitmap = win32gui.SelectObject(hMemDC, hImage)
win32gui.BitBlt(hdc, 50, 50, 50 + 400, 50 + 272, hMemDC, 0, 0, win32con.SRCCOPY) # Image(400, 272) at (50, 50)
# The BitBlt function performs a bit-block transfer of the color data corresponding to a rectangle of pixels
# from the specified source device context into a destination device context.

win32gui.SelectObject(hMemDC, hOldBitmap)
win32gui.DeleteObject(hImage)
win32gui.DeleteDC(hMemDC)
win32gui.ReleaseDC(hWnd, hdc)
