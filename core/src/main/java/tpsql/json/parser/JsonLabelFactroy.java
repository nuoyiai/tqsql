package tpsql.json.parser;

public class JsonLabelFactroy {
	public static IJsonLabel create(JsonLabelType type)
    {
        switch (type)
        {
            case Array:
                return new ArrayLabel();
            case Boolean:
                return new BooleanLabel();
            case Number:
                return new NumberLabel();
            case Object:
                return new ObjectLabel();
            case Pair:
                return new PairLabel();
            case String:
                return new StringLabel();
            case Value:
                return new ValueLabel();
            case Date:
                return new DateLabel();
            case Null:
                return new NullLabel();
            default:
                return null;
        }
    }
}
