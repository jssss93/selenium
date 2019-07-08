from PIL import Image
from PIL import ImageGrab
from PIL import ImageChops
import time

def PixelCheck(x1, y1, x2, y2):
    im1 = ImageGrab.grab((x1, y1, x2, y2))
    # Take a snapshot of the screen. The pixels inside the bounding box are returned as an “RGB” image
    # on Windows or “RGBA” on macOS. If the bounding box is omitted, the entire screen is copied.
    while 1:
        time.sleep(0.1)
        im2 = ImageGrab.grab((x1, y1, x2, y2))
        im = ImageChops.difference(im1, im2)
        # Returns the absolute value of the pixel-by-pixel difference between the two images.
        # 마우스로 인한 변경은 반영 되지 않는다. 같은 이미지이면 difference()의 결과 이미지는 모든 픽셀이 0.
        for y in range(im.height):
            for x in range(im.width):
                if im.getpixel((x, y)) != (0, 0, 0): # Returns the pixel value at a given position.
                    return x, y

x1, y1, x2, y2 = map(int, input("Enter x1, y1, x2, y2 values: ").split())
# x1, y1, x2, y2 = input("Enter x1, y1, x2, y2 values: ").split()
# x1 = int(x1)
# y1 = int(y1)
# x2 = int(x2)
# y2 = int(y2)

coord = PixelCheck(x1, y1, x2, y2) # 추적할 영역의 좌상단, 우하단 좌표
print(x1 + coord[0], y1 + coord[1]) # 추적 영역 중 변화된 영역의 좌상단 좌표(스크린 기준)
