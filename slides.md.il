+++

# The limits of tell don't ask

## What happens if we tell don't ask all the way down?

--

+++

### Long, long ago

An old wise coder said to me:

> This could do with a bit more tell don't ask.

--

## What does that even mean?

--

## What does that even mean?

> Procedural code gets information then makes decisions. Object-oriented code tells objects to do things.
>> Alec Sharp

--

## What does that even mean?

> Procedural code gets information then makes decisions. Object-oriented code tells objects to do things.
>> Alec Sharp

Pithy, but a bit vague.

-+-+

--

+++

## Vagueness

--

### Good

Ambiguity encourages independent thought!

--

### Bad

Ambiguity leads can lead to trolling and misunderstanding.

-+-+

--

+++

### Idea

* Create a simple, extreme definition of TDA
* Attempt to program in that world

-+-+

--

+++

### Definition: Tell Don't Ask

A method call is "Tell Don't Ask" if no information can be returned to the caller from the callee.

--

### Example 1

Is this method TDA?

<pre class="prettyprint">
public void write(byte[] b) throws IOException;
</pre>

--

### Example 2

Is this method TDA?

<pre class="prettyprint">
public void addResultsTo(final List&lt;? super String&gt; out);
</pre>

--

### Example 3

A really sneaky example

<pre class="prettyprint">
public class B {
    private final String secret;

    public B(String secret) {
        this.secret = secret;
    }

    public void doThing(final A a) {
        a.aha(secret);
    }
}

public class A {
    public void doThing(final B b) {
        b.doThing(a);
    }

    public void aha(final String theSecretOfB) {
        // now we have the secret of B! Stupid B!
    }
}
</pre>

--

### Long form definition

A function is tell don’t ask if:

* It has void return type
* It never throws
* All the function’s parameters are either classes, whose functions all obey 1 and 2, or primitives
* None of the function’s parameters may contain a reference to the caller, directly or indirectly

--

### Long form definition

...but really, the short form is the one to pay attention to.

--

### Grey Areas

* Constructors
* Primitives 
* Returning objects whose methods are all TDA

-+-+

--

+++

## BreadShop

A startup determined to deliver bread long after it was needed.

--

## BreadShop

git clone https://github.com/grumpyjames/limits\_of\_tda.git

-+-+