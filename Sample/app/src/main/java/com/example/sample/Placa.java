package com.example.sample;

import java.util.ArrayList;
import java.util.List;

public class Placa {


    Pino[][] matrix = new Pino[16][8];
    public  List<Pino> lista;

    public Placa(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

        double realXDistance = 25.5;
        double realCornerDistance = 0.75;

        double virtualXDisntaceBottom = x2 - x1;

        double virtualXDisntaceTop= x3 -x4;

        double virtualCornerBottomDistance = (realCornerDistance * virtualXDisntaceBottom) / realXDistance;

        double virtualCornerTopDistance = (realCornerDistance * virtualXDisntaceTop) / realXDistance;

        //Bottom
        Point point1= new Point(x1 + virtualCornerBottomDistance,y1 + virtualCornerBottomDistance);
        Point point2= new Point(x2 - virtualCornerBottomDistance ,y2 + virtualCornerBottomDistance );

        //Top
        Point point3= new Point(x3 - virtualCornerTopDistance,y3 - virtualCornerTopDistance);
        Point point4= new Point(x4 + virtualCornerTopDistance ,y4 - virtualCornerTopDistance);


        lista = this.getLegoNubs(point1,point2,point3,point4);

        System.out.println("----------");
        System.out.println(lista.size());


        int k=0;
        for(int i=0; i<matrix.length;i++)
        {
            for ( int j=0;j<matrix[0].length;j++)
            {
                if( k== lista.size())
                {
                    break;
                }
                matrix[i][j]=lista.get(k);
                k++;
            }
        }




    }

    public static List<Pino> getLegoNubs(Point BL, Point BR, Point TR, Point TL) {
        List<Pino> lista= new ArrayList<>();

        /*
        double disntacex= (point2.x-point1.x)/15;
        double disntacey= (point4.y- point1.y)/7;
        double disntacey2= (point4.x- point1.x)/7;
        double m1 = (point2.y-point1.y)/( point2.x-point1.x);
        double m2 = (point4.y-point1.y) / ( point4.x- point1.x);
        double b2 = point1.y - m2 * point1.x;
        double b1 = point1.y- m1* point1.x;
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(disntacey);
        System.out.println((disntacex * 16 + point1.x) * m1 + b1-(disntacey * 8));
        System.out.println((disntacex * 0 + point1.x) * m1 + b1-(disntacey * 0)) ;
        System.out.println(disntacey2);
        //y1=mx1+b
        //Y2=mx2+b
        for( int i = 0; i < 16; i++)
        {
            for ( int j=0; j<8;j++ )
            {
                double x = (disntacex * i + point1.x+disntacey2*j) * m1 + b1+(disntacey * j) ;

                double y = (disntacey * j + point1.y) * m2 + b2 ;
                Pino k= new Pino(disntacex*i+ point1.x+disntacey2*j,x,"green");
                lista.add(k);
            }
        }

         */

        double x2 = BR.x - BL.x;
        double x1= TR.x -TL.x;

        double  increment_x1 = x1/15;
        double increment_x2=x2/15;

        List<Pino> pointsX1 = new ArrayList<>();
        List<Pino> pointsX2 = new ArrayList<>();

        List <Reta> retas= new ArrayList<>();

        double m2 = (BL.y-BR.y)/( BL.x-BR.x);
        double m1 = (TL.y-TR.y) / ( TL.x- TR.x);
        double b1 = TL.y - m1 * TL.x;
        double b2 = BL.y- m2* BL.x;

        for ( int i =0 ; i<16; i++)
        {
            double aux2 = ( BL.x+increment_x2*i );
            double auy2= aux2*m2+b2;

            double aux1 = ( TL.x+increment_x1*i );
            double auy1= aux1*m1+b1;


            pointsX1.add(new Pino(aux1,auy1,"green"));
            pointsX2.add( new  Pino( aux2,auy2,"green"));

        }

        for( int i =0 ; i<pointsX1.size();i++)
        {
            Pino pino1= pointsX1.get(i);
            Pino pino2 = pointsX2.get(i);

            double m= ( pino1.y-pino2.y)/ ( pino1.x-pino2.x);
            double b= pino1.y-m*pino1.x;

            double increment= (pino1.y-pino2.y)/7;

            Reta p= new Reta(m,b,increment);
            retas.add(p);
        }

        for( int i = 0; i<retas.size(); i++)
        {
            for( int j=0; j<8; j++)
            {
                Pino pino1= pointsX1.get(i);
                Pino pino2= pointsX2.get(i);
                Reta reta= retas.get(i);
                double auy = pino2.y+(reta.increment*j);
                double aux =  (auy-reta.b)/reta.m;

                lista.add ( new Pino ( aux,auy,"green"));
            }
        }

        return lista;
    }

    public Pino[][] getMatrix() {
        return matrix;
    }
}
