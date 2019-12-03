import cv2
import os
import shutil

user_num = "01032244433"
vidcap = cv2.VideoCapture('./ICU_cam.avi')
count = 0
ret = True

if not os.path.exists("./images/" + user_num):
            os.makedirs("./images/" + user_num)
else:
    shutil.rmtree("./images" + user_num)

while(ret):
    ret, image = vidcap.read()
 
    if(int(vidcap.get(1)) % 2 == 0):
        print('Saved frame number : ' + str(int(vidcap.get(1))))
        cv2.imwrite("./images/" + user_num + "/%s_%d.jpg" % (user_num, count), image)
        print('Saved frame%d.jpg' % count)
        count += 1

vidcap.release()
