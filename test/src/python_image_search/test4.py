import win32api
import win32gui
import win32con
import win32ui

hWnd = win32gui.GetDesktopWindow()
# Retrieves a handle to the desktop window. The desktop window covers the entire screen.
# The desktop window is the area on top of which other windows are painted.
hdc = win32gui.GetDC(hWnd)
#win32gui.GetDC(None)
# A handle to the window whose DC is to be retrieved. If this value is NULL, GetDC
# retrieves the DC for the entire screen.

red = win32api.RGB(255, 0, 0)
win32gui.SetPixel(hdc, 0, 0, red)  # (0, 0)에 빨간 점 그리기

MyPen = win32gui.CreatePen(win32con.PS_SOLID, 5, win32api.RGB(0,0,255));
OldPen = win32gui.SelectObject(hdc, MyPen);

win32gui.Rectangle(hdc, 50, 50, 100, 100) # (50, 50, 100, 100)에 파란 선으로 사각형 그리기

win32gui.SelectObject(hdc, OldPen);
win32gui.DeleteObject(MyPen);

# 폰트 만들기
font_spec = {'name':'Arial', 'height':42, 'weight':30}
font = win32ui.CreateFont(font_spec)
#lf = win32gui.LOGFONT()
#lf.lfFaceName = "Times New Roman"
#lf.lfHeight = 100
#lf.lfWeight = win32con.FW_NORMAL
#hf = win32gui.CreateFontIndirect(lf)

oldfont = win32gui.SelectObject(hdc, font.GetSafeHandle())

win32gui.SetTextColor(hdc, win32api.RGB(255,0,0))
win32gui.SetBkColor(hdc, win32api.RGB(255,255,0))
#win32gui.SetBkMode(hdc, win32con.TRANSPARENT)
# Desktop window DC로는 SetBKMode()가 잘 작동하지 않는다
print('asdf')
text = 'Software Engineer'
rect = win32gui.GetClientRect(hWnd)
win32gui.DrawText(hdc, text, len(text), rect, win32con.DT_CENTER | win32con.DT_VCENTER
                  | win32con.DT_SINGLELINE | win32con.DT_WORDBREAK)
# 화면 가운데 문자열 출력

win32gui.SelectObject(hdc,oldfont)
win32gui.DeleteObject(font.GetSafeHandle())

win32gui.ReleaseDC(hWnd, hdc)
