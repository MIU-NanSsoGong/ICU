import cv2
import os

filelist = [ f for f in os.listdir("./images/") if f.endswith(".jpg") ]
print(filelist)
for f in filelist:
    os.remove("./images/" + f)


vidcap = cv2.VideoCapture('./ICU_cam.avi')
list_file = open("training_file.txt", 'w') 
count = 0
ret = True
pwd = os.getcwd()
while(ret):
    ret, image = vidcap.read()
 
    if(int(vidcap.get(1)) % 5 == 0):
        print('Saved frame number : ' + str(int(vidcap.get(1))))
        cv2.imwrite("./images/frame%d.jpg" % count, image)
        print('Saved frame%d.jpg' % count)
        list_file.write(pwd + "/frame" + str(count) + ".jpg\n")
        count += 1

vidcap.release()
