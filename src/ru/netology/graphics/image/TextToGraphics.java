package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

import static jdk.internal.org.jline.utils.Colors.h;

public class TextToGraphics implements TextGraphicsConverter {
    int maxWight;
    int maxHeight;
    double maxRatio;
    boolean checkRatio = true;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        try {

            BufferedImage img = ImageIO.read(new URL(url)); //получаем картинку
            if (img == null) {
                throw new IOException("Не удалось загрузить изображение по URL: " + url);
            }
            int width = img.getWidth();
            int height = img.getHeight();

            // Если конвертер попросили проверять на максимально допустимое
            // соотношение сторон изображения, то вам здесь нужно сделать эту проверку,
            // и, если картинка не подходит, выбросить исключение BadImageSizeException.
            // Чтобы получить ширину картинки, вызовите img.getWidth(), высоту - img.getHeight()
            if (checkRatio) {
                double ratio = Math.max((double) width / height, (double) height / width);
                if (ratio > maxRatio) {
                    throw new BadImageSizeException(ratio, maxRatio);
                }
            }

            // Если конвертеру выставили максимально допустимые ширину и/или высоту,
            // вам нужно по ним и по текущим высоте и ширине вычислить новые высоту
            // и ширину.
            // Соблюдение пропорций означает, что вы должны уменьшать ширину и высоту
            // в одинаковое количество раз.
            // Пример 1: макс. допустимые 100x100, а картинка 500x200. Новый размер
            // будет 100x40 (в 5 раз меньше).
            // Пример 2: макс. допустимые 100x30, а картинка 150x15. Новый размер
            // будет 100x10 (в 1.5 раза меньше).

            if (maxHeight != 0 || maxWight != 0) {
                double scale = Math.max(height / maxHeight, width / maxWight); //Вычислим коэффициент масштабирования
                // Вычислим новые размеры изображеия
                int newWidth = (int) (width / scale);
                int newHeight = (int) (height / scale);

                // Теперь нам нужно попросить картинку изменить свои размеры на новые.
                // Последний параметр означает, что мы просим картинку плавно сузиться
                // на новые размеры. В результате мы получаем ссылку на новую картинку, которая
                // представляет собой суженную старую.
                Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

                // Теперь сделаем её чёрно-белой. Для этого поступим так:
                // Создадим новую пустую картинку нужных размеров, заранее указав последним
                // параметром чёрно-белую цветовую палитру:
                BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
                // Попросим у этой картинки инструмент для рисования на ней:
                Graphics2D graphics = bwImg.createGraphics();
                // А этому инструменту скажем, чтобы он скопировал содержимое из нашей суженной картинки:
                graphics.drawImage(scaledImage, 0, 0, null);

                // Теперь в bwImg у нас лежит чёрно-белая картинка нужных нам размеров.
                // Вы можете отслеживать каждый из этапов, в любом удобном для
                // вас моменте сохранив промежуточную картинку в файл через:
                // ImageIO.write(imageObject, "png", new File("out.png"));
                // После вызова этой инструкции у вас в проекте появится файл картинки out.png

                // Теперь давайте пройдёмся по пикселям нашего изображения.
                // Если для рисования мы просили у картинки .createGraphics(),
                // то для прохода по пикселям нам нужен будет этот инструмент:
                WritableRaster bwRaster = bwImg.getRaster();

                // Он хорош тем, что у него мы можем спросить пиксель на нужных
                // нам координатах, указав номер столбца (w) и строки (h)
                // int color = bwRaster.getPixel(w, h, new int[3])[0];
                // Выглядит странно? Согласен. Сам возвращаемый методом пиксель — это
                // массив из трёх интов, обычно это интенсивность красного, зелёного и синего.
                // Но у нашей чёрно-белой картинки цветов нет, и нас интересует
                // только первое значение в массиве. Ещё мы параметром передаём интовый массив на три ячейки.
                // Дело в том, что этот метод не хочет создавать его сам и просит
                // вас сделать это, а сам метод лишь заполнит его и вернёт.
                // Потому что создавать массивы каждый раз слишком медленно. Вы можете создать
                // массив один раз, сохранить в переменную и передавать один
                // и тот же массив в метод, ускорив тем самым программу.

                // TODO Вам осталось пробежаться двойным циклом по всем столбцам (ширина)
                //  и строкам (высота) изображения, на каждой внутренней итерации
                // получить степень белого пикселя (int color выше) и по ней
                // получить соответствующий символ c. Логикой превращения цвета
                // в символ будет заниматься другой объект, который мы рассмотрим ниже
                for???

                {
                    for ???{
                    int color = bwRaster.getPixel(w, h, new int[3])[0];
                    char c = schema.convert(color);
            ??? //запоминаем символ c, например, в двумерном массиве или как-то ещё на ваше усмотрение
                }
                }

                // Осталось собрать все символы в один большой текст.
                // Для того, чтобы изображение не было слишком узким, рекомендую
                // каждый пиксель превращать в два повторяющихся символа, полученных
                // от схемы.


                return???; // Возвращаем собранный текст.
            }

        } catch (IOException | BadImageSizeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWight = width;
    }


    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;

    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;

    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
